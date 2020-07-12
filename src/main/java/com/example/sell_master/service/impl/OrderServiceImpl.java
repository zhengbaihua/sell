package com.example.sell_master.service.impl;

import com.example.sell_master.converter.OrderMaster2OrderDTOConverter;
import com.example.sell_master.dataobject.OrderDetail;
import com.example.sell_master.dataobject.OrderMaster;
import com.example.sell_master.dataobject.ProductInfo;
import com.example.sell_master.dto.CartDTO;
import com.example.sell_master.dto.OrderDTO;
import com.example.sell_master.enums.OrderStatusEnum;
import com.example.sell_master.enums.PayStatusEnum;
import com.example.sell_master.enums.ResultEnum;
import com.example.sell_master.exception.ResponseBankException;
import com.example.sell_master.exception.SellException;
import com.example.sell_master.repository.OrderDetailRepository;
import com.example.sell_master.repository.OrderMasterRepository;
import com.example.sell_master.service.*;
import com.example.sell_master.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @zbh
 * @2020/2/22 15:33
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private PayService payService;
    @Autowired
    private PushMessageService pushMessageService;
    @Autowired
    private WebSocket webSocket;
    @Override
    @Transactional//加一个事务，抛出异常，进行回滚，不做任何操作
    //创建订单
    public OrderDTO create(OrderDTO orderDTO) {
        //创建订单时就随机生成orderId
        String orderId=KeyUtil.genUniqueKey();
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);

//        List<CartDTO> cartDTOList=new ArrayList<>();
        //1.查询商品（数量，价格）
        //只传入了商品的id和商品的数量
        for ( OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo= productService.findOne(orderDetail.getProductId());
            if (productInfo==null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
//               throw new ResponseBankException();
            }
            //2.计算订单总价----某一件的价格（价格x数量）
            orderAmount= productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);

            //把productInfo的属性拷贝到orderDetail
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
//            CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }
        //3.写入订单数据库（orderMaster和orderDetail）
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);//写入数据库
        //4.如果下单成功，扣库存
        List<CartDTO> cartDTOList= orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        // 发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());


        return orderDTO;
    }

    @Override
    //查询订单
    public OrderDTO findOne(String orderId) {
        /**
         * 从数据库查不到就返回null
         * .get 抛异常
         */
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //查询订单详情
        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        //查询订单列表
        Page<OrderMaster> orderMasterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        //创建一个返回对象
        //public PageImpl(List<T> content, Pageable pageable, long total)
        return new PageImpl<OrderDTO>(orderDTOList,pageable,
                orderMasterPage.getTotalElements());

    }

    @Override
    @Transactional
//    取消订单
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();

        //1.判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确,orderId={},orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult= orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            log.error("【取消订单】 更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //3.返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】 订单中无商品详情,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e ->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //4.如果已支付，需要退款
        if (orderDTO.getOrderStatus().equals(PayStatusEnum.SUCCSESS)){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    //订单完结finish
    public OrderDTO finish(OrderDTO orderDTO) {
        //1.判断订单状态
        //如果订单状态不等于新下单,抛异常
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】 订单状态不正确,orderId={},orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        //对象拷贝
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            //如果返回为空,
            log.error("【完结订单】 更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //推送微信模版消息
        pushMessageService.orderStatus(orderDTO);

       // pushMessageService.orderStaus(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    //支付
    public OrderDTO paid(OrderDTO orderDTO) {
        //1.判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单支付成功】 订单状态不正确,orderId={},orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】 订单支付状态不正确,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCSESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if (updateResult==null){
            log.error("【订单支付完成】 更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        //创建一个返回对象
        //public PageImpl(List<T> content, Pageable pageable, long total)
        return new PageImpl<>(orderDTOList,pageable,
                orderMasterPage.getTotalElements());
    }
}

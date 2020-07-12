package com.example.sell_master.controller;

import com.example.sell_master.dataobject.ProductCategory;
import com.example.sell_master.dataobject.ProductInfo;
import com.example.sell_master.exception.SellException;
import com.example.sell_master.form.ProductForm;
import com.example.sell_master.service.CategoryService;
import com.example.sell_master.service.ProductService;
import com.example.sell_master.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 * @zbh
 * @2020/3/2 15:28
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    /**
     * 列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map ){
        PageRequest request=PageRequest.of(page-1,size);
        Page<ProductInfo> productInfoPage =productService.findAll(request);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
//        orderDTOPage.getContent();
        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try{
            productService.onSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        //如果成功
        map.put("url","/sell/seller/product/list");
        return new  ModelAndView("common/success",map);
    }
    /**
     * 商品下架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try{
            productService.offSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        //如果成功
        map.put("url","/sell/seller/product/list");
        return new  ModelAndView("common/success",map);
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                      Map<String,Object> map){
        //如果productId不是空，那么就查询一下内容
        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo=productService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有类目
        List<ProductCategory> categoryList=categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/index",map);
    }

    /**
     * 保存/更新
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        //如果出错

        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo=new ProductInfo();
        try {
            //如果ProductId为空，说明是新增
            if(!StringUtils.isEmpty(form.getProductId())){
                productInfo=productService.findOne(form.getProductId());
            }else {
                form.setProductId(KeyUtil.genUniqueKey());
            }

            //没有出错，进行数据拷贝
            BeanUtils.copyProperties(form,productInfo);
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}

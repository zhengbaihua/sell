package com.example.sell_master.controller;

import com.example.sell_master.VO.ProductInfoVO;
import com.example.sell_master.VO.ProductVO;
import com.example.sell_master.VO.ResultVO;
import com.example.sell_master.dataobject.ProductCategory;
import com.example.sell_master.dataobject.ProductInfo;
import com.example.sell_master.service.CategoryService;
import com.example.sell_master.service.ProductService;
import com.example.sell_master.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * @zbh
 * @2020/2/20 23:13
 */
@CrossOrigin
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ResultVO list(){
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList= productService.findUpAll();
        //2.查询类目（一次性查询）
      /* List<Integer> categoryTypeList=new ArrayList<>();
       //传统方法
        for(ProductInfo productInfo: productInfoList){
            categoryTypeList.add(productInfo.getCategoryType()) ;
        }*/
        //精简方法（java8.lambda）----推荐
         List<Integer> categoryTypeList= productInfoList.stream()
                 .map(e -> e.getCategoryType())
                 .collect(Collectors.toList());
        List<ProductCategory> productCategoryList= categoryService.findByCategoryTypeIn(categoryTypeList);
        //3.数据拼装
        //api最外层ProductVO设一个list
        List<ProductVO> productVOList=new ArrayList<>();
        //遍历类目
        for (ProductCategory productCategory:productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            //遍历商品详情
            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                //
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    //把productInfo的值拷贝到productInfoVO
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


        /*把这一部分封装到utils包下的ResultVOUtil
        ResultVO resultVO= new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;*/


        return ResultVOUtil.success(productVOList);
    }
}

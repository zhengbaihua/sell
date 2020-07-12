package com.example.sell_master.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品（包含类目）
 * @zbh
 * @2020/2/21 19:27
 */
@Data
public class ProductVO {
    @JsonProperty("name")
    //加上注解 ，因为返回给前端需要的是name
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}

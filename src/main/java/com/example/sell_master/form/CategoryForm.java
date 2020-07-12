package com.example.sell_master.form;

import lombok.Data;

/**
 * @zbh
 * @2020/3/2 22:32
 */
@Data
public class CategoryForm {
    private Integer categoryId;
    /**类目名字 */
    private String categoryName;
    /**类目编号 */
    private Integer categoryType;
}

package com.example.sell_master.dataobject.mapper;

import com.example.sell_master.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @zbh
 * @2020/3/6 16:39
 */
public interface ProductCategoryMapper {
    /**
     * 通过map的方式写入
     * @param map
     * @return
     */
    @Insert("insert into product_category(category_name,category_type) values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String ,Object> map);

    /**
     * 通过对象object写入(用的较多)
     * @param productCategory
     * @return
     */
    @Insert("insert into product_category(category_name,category_type) values (#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);


    /**
     * 查询categoryType
     * 由于categoryType是约束索引，一次只能查出一条
     * @param categoryType
     * @return
     */
    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType")
    })
    ProductCategory findbyCategoryType(Integer categoryType);

    /**
     * 查询categoryName
     * 由于，，，，所以结果不唯一
     * @param categoryName
     * @return
     */
    @Select("select * from product_category where category_name = #{categoryName}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType")
    })

    List<ProductCategory> findbyCategoryName(String categoryName);

    /**
     * 通过某一个字段去更新
     * @param categoryName
     * @param categoryType
     * @return
     */
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByCategoryType(@Param("categoryName") String  categoryName,
                             @Param("categoryType")Integer categoryType);

    /**
     * 根据一个对象去更新
     * @param productCategory
     * @return
     */
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByObjct(ProductCategory productCategory);


    /**
     * 通过type来删除
     * @param categoryType
     * @return
     */
    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategoryType(Integer categoryType);

    ProductCategory selectByCategoryType(Integer categoryType);

}

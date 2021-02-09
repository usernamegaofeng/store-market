package pers.store.market.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/9 下午9:35
 * @description: 二级分类vo
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryLevel2Vo {

    private String id;
    private String name;
    /**
     * 一级父分类的id
     */
    private String catalog1Id;

    /**
     * 三级子分类
     */
    private List<CategoryLevel3Vo> catalog3List;


    /**
     * 三级分类vo
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryLevel3Vo {

        /**
         * 父分类、二级分类id
         */
        private String catalog2Id;
        private String id;
        private String name;
    }
}

package pers.store.market.common.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/9 下午2:42
 * @description: sku的elasticsearch检索索引model
 */
@Data
public class SkuEsModel {

    private Long skuId;
    private Long spuId;
    private String skuTitle;
    private BigDecimal skuPrice;
    private String skuImg;
    private Long saleCount;
    private Boolean hasStock;
    private Long hotScore;
    private Long brandId;
    private Long catalogId;
    private String brandName;
    private String brandImg;
    private String catalogName;
    private List<Attrs> attrs;

    @Data
    public static class Attrs {
        private Long attrId;
        private String attrName;
        private String attrValue;
    }
}

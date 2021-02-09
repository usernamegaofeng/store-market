package pers.store.market.common.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuReductionDto {

    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}

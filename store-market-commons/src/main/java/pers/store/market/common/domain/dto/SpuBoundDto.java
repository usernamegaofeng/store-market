package pers.store.market.common.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpuBoundDto {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}

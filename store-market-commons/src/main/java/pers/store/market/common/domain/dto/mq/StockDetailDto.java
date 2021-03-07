package pers.store.market.common.domain.dto.mq;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/3/6 下午10:31
 * @description: 库存单详情dto
 */
@Data
public class StockDetailDto {
    /**
     * 库存单详情ID
     */
    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * sku_name
     */
    private String skuName;
    /**
     * 购买个数
     */
    private Integer skuNum;
    /**
     * 工作单id
     */
    private Long taskId;

    /**
     * 仓库id
     */
    private Long wareId;

    /**
     * 锁定状态
     */
    private Integer lockStatus;
}

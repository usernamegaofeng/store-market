package pers.store.market.common.domain.dto.mq;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/3/6 下午10:32
 * @description: 库存锁定dto
 */
@Data
public class StockLockedDto {
    private Long id;   //库存工作单ID
    private StockDetailDto detailDto; //库存工作单详情实体类
}

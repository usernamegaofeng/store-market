package pers.store.market.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 库存工作单
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:46
 */
@Data
@TableName("wms_ware_order_task_detail")
@ApiModel(description = "库存工作单实体类")
public class WareOrderTaskDetailEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * sku_id
     */
    @ApiModelProperty(value = "sku_id")
    private Long skuId;
    /**
     * sku_name
     */
    @ApiModelProperty(value = "sku_name")
    private String skuName;
    /**
     * 购买个数
     */
    @ApiModelProperty(value = "购买个数")
    private Integer skuNum;
    /**
     * 工作单id
     */
    @ApiModelProperty(value = "工作单id")
    private Long taskId;
    /**
     * 仓库id
     */
    @ApiModelProperty(value = "仓库id")
    private Long wareId;
    /**
     * 1-已锁定  2-已解锁  3-扣减
     */
    @ApiModelProperty(value = "1-已锁定  2-已解锁  3-扣减")
    private Integer lockStatus;

}

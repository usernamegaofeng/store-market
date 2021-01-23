package pers.store.market.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品库存
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:46
 */
@Data
@TableName("wms_ware_sku")
@ApiModel(description = "商品库存实体类")
public class WareSkuEntity implements Serializable {

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
     * 仓库id
     */
    @ApiModelProperty(value = "仓库id")
    private Long wareId;
    /**
     * 库存数
     */
    @ApiModelProperty(value = "库存数")
    private Integer stock;
    /**
     * sku_name
     */
    @ApiModelProperty(value = "sku_name")
    private String skuName;
    /**
     * 锁定库存
     */
    @ApiModelProperty(value = "锁定库存")
    private Integer stockLocked;

}

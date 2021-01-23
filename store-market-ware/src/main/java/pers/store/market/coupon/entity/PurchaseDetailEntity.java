package pers.store.market.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:45
 */
@Data
@TableName("wms_purchase_detail")
@ApiModel(description = "采购详情实体类")
public class PurchaseDetailEntity implements Serializable {

    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "采购单id")
    private Long purchaseId;
    @ApiModelProperty(value = "采购商品id")
    private Long skuId;
    @ApiModelProperty(value = "采购数量")
    private Integer skuNum;
    @ApiModelProperty(value = "采购金额")
    private BigDecimal skuPrice;
    @ApiModelProperty(value = "仓库id")
    private Long wareId;
    @ApiModelProperty(value = "状态[0新建，1已分配，2正在采购，3已完成，4采购失败]")
    private Integer status;

}

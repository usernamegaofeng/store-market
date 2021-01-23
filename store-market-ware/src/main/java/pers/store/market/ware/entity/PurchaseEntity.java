package pers.store.market.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 采购信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:45
 */
@Data
@TableName("wms_purchase")
@ApiModel(description = "采购信息实体类")
public class PurchaseEntity implements Serializable {


    @TableId
    @ApiModelProperty(value = "")
    private Long id;
    @ApiModelProperty(value = "")
    private Long assigneeId;
    @ApiModelProperty(value = "")
    private String assigneeName;
    @ApiModelProperty(value = "")
    private String phone;
    @ApiModelProperty(value = "")
    private Integer priority;
    @ApiModelProperty(value = "")
    private Integer status;
    @ApiModelProperty(value = "")
    private Long wareId;
    @ApiModelProperty(value = "")
    private BigDecimal amount;
    @ApiModelProperty(value = "")
    private Date createTime;
    @ApiModelProperty(value = "")
    private Date updateTime;

}

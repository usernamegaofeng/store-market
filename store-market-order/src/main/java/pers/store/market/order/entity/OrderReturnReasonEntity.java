package pers.store.market.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 退货原因
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:40:14
 */
@Data
@TableName("oms_order_return_reason")
@ApiModel(description = "退货原因实体类")
public class OrderReturnReasonEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 退货原因名
     */
    @ApiModelProperty(value = "退货原因名")
    private String name;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 启用状态
     */
    @ApiModelProperty(value = "启用状态")
    private Integer status;
    /**
     * create_time
     */
    @ApiModelProperty(value = "create_time")
    private Date createTime;

}

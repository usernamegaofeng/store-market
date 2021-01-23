package pers.store.market.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 秒杀活动场次
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:37:42
 */
@Data
@TableName("sms_seckill_session")
@ApiModel(description = "秒杀活动场次实体类")
public class SeckillSessionEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 场次名称
     */
    @ApiModelProperty(value = "场次名称")
    private String name;
    /**
     * 每日开始时间
     */
    @ApiModelProperty(value = "每日开始时间")
    private Date startTime;
    /**
     * 每日结束时间
     */
    @ApiModelProperty(value = "每日结束时间")
    private Date endTime;
    /**
     * 启用状态
     */
    @ApiModelProperty(value = "启用状态")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}

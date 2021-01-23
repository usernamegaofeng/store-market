package pers.store.market.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 秒杀活动
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:37:43
 */
@Data
@TableName("sms_seckill_promotion")
@ApiModel(description = "秒杀活动实体类")
public class SeckillPromotionEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 活动标题
     */
    @ApiModelProperty(value = "活动标题")
    private String title;
    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    private Date startTime;
    /**
     * 结束日期
     */
    @ApiModelProperty(value = "结束日期")
    private Date endTime;
    /**
     * 上下线状态
     */
    @ApiModelProperty(value = "上下线状态")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long userId;

}

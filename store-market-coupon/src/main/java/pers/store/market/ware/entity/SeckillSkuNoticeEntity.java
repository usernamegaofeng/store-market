package pers.store.market.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 秒杀商品通知订阅
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:37:42
 */
@Data
@TableName("sms_seckill_sku_notice")
@ApiModel(description = "秒杀商品通知订阅实体类")
public class SeckillSkuNoticeEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * member_id
     */
    @ApiModelProperty(value = "member_id")
    private Long memberId;
    /**
     * sku_id
     */
    @ApiModelProperty(value = "sku_id")
    private Long skuId;
    /**
     * 活动场次id
     */
    @ApiModelProperty(value = "活动场次id")
    private Long sessionId;
    /**
     * 订阅时间
     */
    @ApiModelProperty(value = "订阅时间")
    private Date subcribeTime;
    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    private Date sendTime;
    /**
     * 通知方式[0-短信，1-邮件]
     */
    @ApiModelProperty(value = "通知方式[0-短信，1-邮件]")
    private Integer noticeType;

}

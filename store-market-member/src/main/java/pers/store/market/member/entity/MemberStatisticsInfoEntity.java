package pers.store.market.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员统计信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:34:32
 */
@Data
@TableName("ums_member_statistics_info")
@ApiModel(description = "会员统计信息实体类")
public class MemberStatisticsInfoEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private Long memberId;
    /**
     * 累计消费金额
     */
    @ApiModelProperty(value = "累计消费金额")
    private BigDecimal consumeAmount;
    /**
     * 累计优惠金额
     */
    @ApiModelProperty(value = "累计优惠金额")
    private BigDecimal couponAmount;
    /**
     * 订单数量
     */
    @ApiModelProperty(value = "订单数量")
    private Integer orderCount;
    /**
     * 优惠券数量
     */
    @ApiModelProperty(value = "优惠券数量")
    private Integer couponCount;
    /**
     * 评价数
     */
    @ApiModelProperty(value = "评价数")
    private Integer commentCount;
    /**
     * 退货数量
     */
    @ApiModelProperty(value = "退货数量")
    private Integer returnOrderCount;
    /**
     * 登录次数
     */
    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;
    /**
     * 关注数量
     */
    @ApiModelProperty(value = "关注数量")
    private Integer attendCount;
    /**
     * 粉丝数量
     */
    @ApiModelProperty(value = "粉丝数量")
    private Integer fansCount;
    /**
     * 收藏的商品数量
     */
    @ApiModelProperty(value = "收藏的商品数量")
    private Integer collectProductCount;
    /**
     * 收藏的专题活动数量
     */
    @ApiModelProperty(value = "收藏的专题活动数量")
    private Integer collectSubjectCount;
    /**
     * 收藏的评论数量
     */
    @ApiModelProperty(value = "收藏的评论数量")
    private Integer collectCommentCount;
    /**
     * 邀请的朋友数量
     */
    @ApiModelProperty(value = "邀请的朋友数量")
    private Integer inviteFriendCount;

}

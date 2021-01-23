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
 * 会员等级
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:34:32
 */
@Data
@TableName("ums_member_level")
@ApiModel(description = "会员等级实体类")
public class MemberLevelEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 等级名称
     */
    @ApiModelProperty(value = "等级名称")
    private String name;
    /**
     * 等级需要的成长值
     */
    @ApiModelProperty(value = "等级需要的成长值")
    private Integer growthPoint;
    /**
     * 是否为默认等级[0->不是；1->是]
     */
    @ApiModelProperty(value = "是否为默认等级[0->不是；1->是]")
    private Integer defaultStatus;
    /**
     * 免运费标准
     */
    @ApiModelProperty(value = "免运费标准")
    private BigDecimal freeFreightPoint;
    /**
     * 每次评价获取的成长值
     */
    @ApiModelProperty(value = "每次评价获取的成长值")
    private Integer commentGrowthPoint;
    /**
     * 是否有免邮特权
     */
    @ApiModelProperty(value = "是否有免邮特权")
    private Integer priviledgeFreeFreight;
    /**
     * 是否有会员价格特权
     */
    @ApiModelProperty(value = "是否有会员价格特权")
    private Integer priviledgeMemberPrice;
    /**
     * 是否有生日特权
     */
    @ApiModelProperty(value = "是否有生日特权")
    private Integer priviledgeBirthday;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

}

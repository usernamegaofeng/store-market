package pers.store.market.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 成长值变化历史记录
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:34:31
 */
@Data
@TableName("ums_growth_change_history")
@ApiModel(description = "成长值变化历史记录实体类")
public class GrowthChangeHistoryEntity implements Serializable {

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
     * create_time
     */
    @ApiModelProperty(value = "create_time")
    private Date createTime;
    /**
     * 改变的值（正负计数）
     */
    @ApiModelProperty(value = "改变的值（正负计数）")
    private Integer changeCount;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;
    /**
     * 积分来源[0-购物，1-管理员修改]
     */
    @ApiModelProperty(value = "积分来源[0-购物，1-管理员修改]")
    private Integer sourceType;

}

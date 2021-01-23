package pers.store.market.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 积分变化历史记录
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:34:31
 */
@Data
@TableName("ums_integration_change_history")
@ApiModel(description = "积分变化历史记录实体类")
public class IntegrationChangeHistoryEntity implements Serializable {

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
     * 变化的值
     */
    @ApiModelProperty(value = "变化的值")
    private Integer changeCount;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;
    /**
     * 来源[0->购物；1->管理员修改;2->活动]
     */
    @ApiModelProperty(value = "来源[0->购物；1->管理员修改;2->活动]")
    private Integer sourceTyoe;

}

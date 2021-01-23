package pers.store.market.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员收藏的专题活动
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:34:32
 */
@Data
@TableName("ums_member_collect_subject")
@ApiModel(description = "会员收藏的专题活动实体类")
public class MemberCollectSubjectEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * subject_id
     */
    @ApiModelProperty(value = "subject_id")
    private Long subjectId;
    /**
     * subject_name
     */
    @ApiModelProperty(value = "subject_name")
    private String subjectName;
    /**
     * subject_img
     */
    @ApiModelProperty(value = "subject_img")
    private String subjectImg;
    /**
     * 活动url
     */
    @ApiModelProperty(value = "活动url")
    private String subjectUrll;

}

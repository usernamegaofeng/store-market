package pers.store.market.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员收藏的商品
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:34:32
 */
@Data
@TableName("ums_member_collect_spu")
@ApiModel(description = "会员收藏的商品实体类")
public class MemberCollectSpuEntity implements Serializable {

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
     * spu_id
     */
    @ApiModelProperty(value = "spu_id")
    private Long spuId;
    /**
     * spu_name
     */
    @ApiModelProperty(value = "spu_name")
    private String spuName;
    /**
     * spu_img
     */
    @ApiModelProperty(value = "spu_img")
    private String spuImg;
    /**
     * create_time
     */
    @ApiModelProperty(value = "create_time")
    private Date createTime;

}

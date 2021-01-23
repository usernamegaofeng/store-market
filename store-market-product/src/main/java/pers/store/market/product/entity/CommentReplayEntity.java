package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品评价回复关系
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Data
@TableName("pms_comment_replay")
@ApiModel(description = "商品评价回复关系实体类")
public class CommentReplayEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Long commentId;
    /**
     * 回复id
     */
    @ApiModelProperty(value = "回复id")
    private Long replyId;

}

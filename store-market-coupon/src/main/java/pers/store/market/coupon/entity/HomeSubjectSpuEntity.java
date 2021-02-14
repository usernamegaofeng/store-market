package pers.store.market.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 专题商品
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:37:43
 */
@Data
@TableName("sms_home_subject_spu")
@ApiModel(description = "专题商品实体类")
public class HomeSubjectSpuEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 专题名字
     */
    @ApiModelProperty(value = "专题名字")
    private String name;
    /**
     * 专题id
     */
    @ApiModelProperty(value = "专题id")
    private Long subjectId;
    /**
     * spu_id
     */
    @ApiModelProperty(value = "spu_id")
    private Long spuId;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

}

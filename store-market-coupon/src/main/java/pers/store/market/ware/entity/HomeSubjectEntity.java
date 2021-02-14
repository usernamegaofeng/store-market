package pers.store.market.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:37:43
 */
@Data
@TableName("sms_home_subject")
@ApiModel(description = "首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】实体类")
public class HomeSubjectEntity implements Serializable {

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
     * 专题标题
     */
    @ApiModelProperty(value = "专题标题")
    private String title;
    /**
     * 专题副标题
     */
    @ApiModelProperty(value = "专题副标题")
    private String subTitle;
    /**
     * 显示状态
     */
    @ApiModelProperty(value = "显示状态")
    private Integer status;
    /**
     * 详情连接
     */
    @ApiModelProperty(value = "详情连接")
    private String url;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 专题图片地址
     */
    @ApiModelProperty(value = "专题图片地址")
    private String img;

}

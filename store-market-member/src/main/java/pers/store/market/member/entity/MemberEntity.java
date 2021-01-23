package pers.store.market.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:34:31
 */
@Data
@TableName("ums_member")
@ApiModel(description = "会员实体类")
public class MemberEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 会员等级id
     */
    @ApiModelProperty(value = "会员等级id")
    private Long levelId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobile;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String header;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Integer gender;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private Date birth;
    /**
     * 所在城市
     */
    @ApiModelProperty(value = "所在城市")
    private String city;
    /**
     * 职业
     */
    @ApiModelProperty(value = "职业")
    private String job;
    /**
     * 个性签名
     */
    @ApiModelProperty(value = "个性签名")
    private String sign;
    /**
     * 用户来源
     */
    @ApiModelProperty(value = "用户来源")
    private Integer sourceType;
    /**
     * 积分
     */
    @ApiModelProperty(value = "积分")
    private Integer integration;
    /**
     * 成长值
     */
    @ApiModelProperty(value = "成长值")
    private Integer growth;
    /**
     * 启用状态
     */
    @ApiModelProperty(value = "启用状态")
    private Integer status;
    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    private Date createTime;

}

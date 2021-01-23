package pers.store.market.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员收货地址
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:34:32
 */
@Data
@TableName("ums_member_receive_address")
@ApiModel(description = "会员收货地址实体类")
public class MemberReceiveAddressEntity implements Serializable {

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
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名")
    private String name;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;
    /**
     * 邮政编码
     */
    @ApiModelProperty(value = "邮政编码")
    private String postCode;
    /**
     * 省份/直辖市
     */
    @ApiModelProperty(value = "省份/直辖市")
    private String province;
    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String city;
    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String region;
    /**
     * 详细地址(街道)
     */
    @ApiModelProperty(value = "详细地址(街道)")
    private String detailAddress;
    /**
     * 省市区代码
     */
    @ApiModelProperty(value = "省市区代码")
    private String areacode;
    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认")
    private Integer defaultStatus;

}

package pers.store.market.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:45
 */
@Data
@TableName("undo_log")
@ApiModel(description = "实体类")
public class UndoLogEntity implements Serializable {

    /**
     *
     */
    @TableId
    @ApiModelProperty(value = "")
    private Long id;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long branchId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String xid;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String context;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private byte[] rollbackInfo;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Integer logStatus;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date logCreated;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date logModified;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String ext;

}

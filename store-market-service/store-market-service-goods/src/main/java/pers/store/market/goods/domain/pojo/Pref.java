package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Table(name = "tb_pref")
public class Pref implements Serializable {

    @Id
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "分类ID")
    private Integer cateId;
    @ApiModelProperty(value = "消费金额")
    private Integer buyMoney;
    @ApiModelProperty(value = "优惠金额")
    private Integer preMoney;
    @ApiModelProperty(value = "活动开始日期")
    private Date startTime;
    @ApiModelProperty(value = "活动截至日期")
    private Date endTime;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "状态")
    private String state;


}

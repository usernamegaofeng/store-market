package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.store.market.goods.domain.dto.SkuDTO;
import pers.store.market.goods.domain.dto.SpuDTO;

import javax.persistence.Table;
import java.util.List;

@Data
@ApiModel(value = "Goods",description = "商品组合实体类")
@Table(name = "tb_goods")
public class Goods {

    @ApiModelProperty(value = "spu")
    private SpuDTO spu;
    @ApiModelProperty(value = "sku列表")
    private List<SkuDTO> skuList;

}

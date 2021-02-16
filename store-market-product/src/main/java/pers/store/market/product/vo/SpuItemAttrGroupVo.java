package pers.store.market.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/16 下午8:33
 * @description: 详情页spu分组vo
 */
@Data
public class SpuItemAttrGroupVo {

    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 分组下的属性名称和属性值
     */
    private List<Attr> attrs;
}

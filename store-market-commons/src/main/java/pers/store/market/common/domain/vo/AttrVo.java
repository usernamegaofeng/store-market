package pers.store.market.common.domain.vo;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/1/29 下午1:57
 * @description: 通用attrVo
 */
@Data
public class AttrVo {

    private Long attrId;
    private String attrName;
    private Integer searchType;
    private String icon;
    private Integer valueType;
    private String valueSelect;
    private Integer attrType;
    private Long enable;
    private Long catelogId;
    private Integer showDesc;
    private Long attrGroupId;
    private Integer sort;
    private String groupName;
    private String catelogName;
    private Long[] catelogPath;
}

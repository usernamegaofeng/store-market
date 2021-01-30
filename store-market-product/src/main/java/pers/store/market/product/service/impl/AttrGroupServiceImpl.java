package pers.store.market.product.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;
import pers.store.market.product.dao.AttrAttrgroupRelationDao;
import pers.store.market.product.dao.AttrGroupDao;
import pers.store.market.product.entity.AttrAttrgroupRelationEntity;
import pers.store.market.product.entity.AttrGroupEntity;
import pers.store.market.product.service.AttrGroupService;
import pers.store.market.product.vo.AttrRelationVo;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    private final int DEFAULT_ZERO = 0; //查询全部数据

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );
        return new PageUtils(page);
    }

    /**
     * 根据三级分类ID获取分类属性分组
     *
     * @param params     分页数据
     * @param categoryId 分类ID
     * @return PageUtils
     */
    @Override
    public PageUtils queryPageByCategoryId(Map<String, Object> params, Long categoryId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<AttrGroupEntity>();
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.and(consumer -> consumer.eq("attr_group_id", key))
                    .or().like("attr_group_name", key);
        }
        if (categoryId != DEFAULT_ZERO) {
            queryWrapper.eq("catelog_id", categoryId);
        }
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }

    /**
     * 批量删除属性与分组的关联关系
     *
     * @param relationVos 参数数组
     */
    @Override
    public void removeAttrRelation(AttrRelationVo[] relationVos) {
        List<AttrRelationVo> attrRelationList = Arrays.asList(relationVos);
        //查询所有的属性关系分组
        List<AttrAttrgroupRelationEntity> relationEntityList = attrRelationList.stream().map(attrRelationVo -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(attrRelationVo, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        attrAttrgroupRelationDao.removeBatchAttrRelation(relationEntityList);
    }
}
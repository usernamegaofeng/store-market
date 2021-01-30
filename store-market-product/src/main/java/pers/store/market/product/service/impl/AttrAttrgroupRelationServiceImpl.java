package pers.store.market.product.service.impl;

import org.springframework.beans.BeanUtils;
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
import pers.store.market.product.entity.AttrAttrgroupRelationEntity;
import pers.store.market.product.service.AttrAttrgroupRelationService;
import pers.store.market.product.vo.AttrRelationVo;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 添加属性与分组关联关系
     *
     * @param relationVos 参数数组
     */
    @Override
    public void addRelation(AttrRelationVo[] relationVos) {
        List<AttrRelationVo> relationVoList = Arrays.asList(relationVos);
        List<AttrAttrgroupRelationEntity> relationEntityList = relationVoList.stream().map(item -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        this.saveBatch(relationEntityList);
    }

}
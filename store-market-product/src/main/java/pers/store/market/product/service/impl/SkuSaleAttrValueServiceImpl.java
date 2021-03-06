package pers.store.market.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;

import pers.store.market.product.dao.SkuSaleAttrValueDao;
import pers.store.market.product.entity.SkuSaleAttrValueEntity;
import pers.store.market.product.service.SkuSaleAttrValueService;
import pers.store.market.product.vo.SkuItemSaleAttrVo;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuItemSaleAttrVo> getSaleAttrBySpuId(Long spuId) {
       return this.baseMapper.getSaleAttrBySpuId(spuId);
    }

    @Override
    public List<String> getSkuSaleAttrAsStringList(Long skuId) {
       return this.baseMapper.getSkuSaleAttrAsStringList(skuId);
    }

}
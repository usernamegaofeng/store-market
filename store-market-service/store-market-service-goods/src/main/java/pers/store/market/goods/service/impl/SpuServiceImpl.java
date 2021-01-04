package pers.store.market.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.store.market.common.utils.IdWorker;
import pers.store.market.goods.dao.BrandMapper;
import pers.store.market.goods.dao.CategoryMapper;
import pers.store.market.goods.dao.SkuMapper;
import pers.store.market.goods.dao.SpuMapper;
import pers.store.market.goods.domain.dto.SkuDTO;
import pers.store.market.goods.domain.dto.SpuDTO;
import pers.store.market.goods.domain.pojo.*;
import pers.store.market.goods.service.ISpuService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/4 上午11:21
 * <p>
 * spu服务实现类
 */
@Service
public class SpuServiceImpl implements ISpuService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 保存商品,SPU+SKU列表
     *
     * @param goods 商品组合实体类
     */
    @Override
    public void add(Goods goods) {
        //spuId放入SPU对象中
        Spu spu = new Spu();
        SpuDTO spuDTO = goods.getSpu();
        BeanUtils.copyProperties(spuDTO, spu);
        spu.setId(String.valueOf(idWorker.nextId()));
        spu.setParaItems(JSONObject.toJSONString(spuDTO.getParaItems()));
        spu.setSpecItems(JSONObject.toJSONString(spuDTO.getSpecItems()));
        //添加保存SPU数据
        spuMapper.insertSelective(spu);
        //保存sku集合数据到数据库
        saveSkuList(goods);
    }

    /**
     * 保存sku列表
     *
     * @param goods 商品组合实体类
     */
    private void saveSkuList(Goods goods) {
        //获取spu对象
        SpuDTO spuDTO = goods.getSpu();
        //当前日期
        Date date = new Date();
        //获取品牌对象
        Brand brand = brandMapper.selectByPrimaryKey(spuDTO.getBrandId());
        //获取分类对象
        Category category = categoryMapper.selectByPrimaryKey(spuDTO.getCategory3Id());
        //获取sku集合对象
        List<SkuDTO> skuList = goods.getSkuList();
        if (skuList != null) {
            for (SkuDTO skuDTO : skuList) {
                //创建sku对象,设置sku主键ID
                Sku sku = new Sku();
                //设置sku规格
                if (skuDTO.getSpec() == null) {
                    sku.setSpec("{}");
                }
                //设置sku名称(商品名称 + 规格)
                String name = skuDTO.getName();
                //将规格json字符串转换成Map
                Map<String, String> specMap = skuDTO.getSpec();
                if (specMap != null && specMap.size() > 0) {
                    for (String value : specMap.values()) {
                        name += " " + value;
                    }
                }
                //复制基本值
                BeanUtils.copyProperties(skuDTO, sku);
                sku.setId(String.valueOf(idWorker.nextId()));
                sku.setName(name);//名称
                sku.setSpuId(spuDTO.getId());//设置spu的ID
                sku.setCreateTime(date);//创建日期
                sku.setUpdateTime(date);//修改日期
                sku.setCategoryId(category.getId());//商品分类ID
                sku.setCategoryName(category.getName());//商品分类名称
                sku.setBrandName(brand.getName());//品牌名称
                skuMapper.insertSelective(sku);//插入sku表数据
            }
        }
    }


}

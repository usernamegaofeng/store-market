package pers.store.market.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import pers.store.market.common.constant.ProductConstant;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;

import pers.store.market.product.dao.AttrAttrgroupRelationDao;
import pers.store.market.product.dao.AttrDao;
import pers.store.market.product.dao.AttrGroupDao;
import pers.store.market.product.dao.CategoryDao;
import pers.store.market.product.entity.AttrAttrgroupRelationEntity;
import pers.store.market.product.entity.AttrEntity;
import pers.store.market.product.entity.AttrGroupEntity;
import pers.store.market.product.entity.CategoryEntity;
import pers.store.market.product.service.AttrService;
import pers.store.market.product.service.CategoryService;
import pers.store.market.product.vo.AttrVo;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    private final String BASE = "base";  //方法请求路径,base代表基本属性,sale代表销售属性

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private AttrGroupDao attrGroupDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );
        return new PageUtils(page);
    }

    /**
     * 保存属性
     *
     * @param attr 接收前端请求参数
     */
    @Override
    @Transactional
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);
        //当属性类型为基本类型的时候,设置属性分组关系
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity attrGroupEntity = new AttrAttrgroupRelationEntity();
            attrGroupEntity.setAttrGroupId(attr.getAttrGroupId());
            attrGroupEntity.setAttrId(attrEntity.getAttrId());
            attrGroupEntity.setAttrSort(attr.getSort());
            attrAttrgroupRelationDao.insert(attrGroupEntity);
        }
    }

    /**
     * 查询基本属性列表,包括基本属性和销售属性
     *
     * @param params     分页参数
     * @param categoryId 分类ID
     * @param type       请求方法路径类型
     * @return PageUtils
     */
    @Override
    public PageUtils queryBasePage(Map<String, Object> params, Long categoryId, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type", BASE.equalsIgnoreCase(type) ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.and(obj -> obj.eq("attr_id", key).or().like("attr_name", key));
        }
        if (categoryId != 0) {
            queryWrapper.eq("catelog_id", categoryId);
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> pageRecords = page.getRecords();
        List<AttrVo> dataList = pageRecords.stream().map(attrEntity -> {
            AttrVo attrVo = new AttrVo();
            BeanUtils.copyProperties(attrEntity, attrVo);
            //查询基础属性的时候才需要属性分组
            if (BASE.equalsIgnoreCase(type)) {
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if (attrAttrgroupRelationEntity != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                    attrVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrVo.getCatelogId());
            if (categoryEntity != null) {
                attrVo.setCatelogName(categoryEntity.getName());
            }
            return attrVo;
        }).collect(Collectors.toList());
        pageUtils.setList(dataList);
        return pageUtils;
    }

    /**
     * 查询属性所有信息
     *
     * @param attrId 属性ID
     * @return
     */
    @Override
    public AttrVo getAttrDetail(Long attrId) {
        AttrVo attrVo = new AttrVo();
        AttrEntity attrEntity = this.getById(attrId);
        if (attrEntity != null) {
            BeanUtils.copyProperties(attrEntity, attrVo);
            if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
                //基本属性的时候需要设置属性分组
                AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
                if (relationEntity != null) {
                    attrVo.setSort(relationEntity.getAttrSort());
                    attrVo.setAttrGroupId(relationEntity.getAttrGroupId());
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    if (attrGroupEntity != null) {
                        attrVo.setGroupName(attrGroupEntity.getAttrGroupName());
                    }
                }
            }
            //设置分类
            Long[] categoryPath = categoryService.findCategoryPath(attrEntity.getCatelogId());
            attrVo.setCatelogPath(categoryPath);
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrVo.setCatelogName(categoryEntity.getName());
            }
        }
        return attrVo;
    }

    /**
     * 修改操作,同时要修改属性分组数据
     *
     * @param attr 前端接收参数
     */
    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);
        //当属性类型为基本类型的时候,需要修改属性分组
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrSort(attr.getSort());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (count > 0) {
                //判断是否存在记录,有记录进行修改
                attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            } else {
                //新增
                attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
            }
        }
    }

    /**
     * 获取属性分组的关联的所有属性
     *
     * @param attrGroupId 属性分组ID
     * @return List<AttrEntity>
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));
        List<Long> ids = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        if (ids.size() != 0) {
            return this.listByIds(ids);
        }
        return null;
    }
}
package pers.store.market.goods.service;

import pers.store.market.goods.domain.pojo.Goods;

/**
 * @author 高枫
 * @date 2021/1/4 上午11:19
 * <p>
 * spu服务接口
 */
public interface ISpuService {

    /**
     * 新增
     *
     * @param goods 商品实体类
     */
    void add(Goods goods);

    /**
     * 修改
     *
     * @param goods 商品实体类
     */
    void update(Goods goods);

    /**
     * 商品审核
     *
     * @param spuId spuId
     */
    void audit(Long spuId);

    /**
     * 下架商品
     *
     * @param spuId spuId
     */
    void pull(Long spuId);

    /**
     * 上架商品
     *
     * @param spuId spuId
     */
    void put(Long spuId);

    /**
     * 逻辑删除
     *
     * @param spuId spuId
     */
    void delete(Long spuId);


    /**
     * 恢复数据
     *
     * @param spuId spuId
     */
    void restore(Long spuId);

    /**
     * 物理删除
     *
     * @param spuId spuId
     */
    void realDelete(Long spuId);
}

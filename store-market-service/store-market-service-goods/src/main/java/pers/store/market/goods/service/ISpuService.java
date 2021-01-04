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
}

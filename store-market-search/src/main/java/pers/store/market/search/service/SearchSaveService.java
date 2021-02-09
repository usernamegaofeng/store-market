package pers.store.market.search.service;

import pers.store.market.common.domain.model.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/9 下午4:27
 * @description: 索引接口
 */
public interface SearchSaveService {

    boolean saveIndex(List<SkuEsModel> skuEsModelList) throws IOException;
}

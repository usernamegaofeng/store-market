package pers.store.market.search.service;

import pers.store.market.search.vo.SearchParam;
import pers.store.market.search.vo.SearchResult;

/**
 * @author Gaofeng
 * @date 2021/2/14 下午7:58
 * @description: 首页搜索接口
 */
public interface SearchService {

    SearchResult search(SearchParam searchParam);
}

package pers.store.market.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.store.market.common.domain.model.SkuEsModel;
import pers.store.market.common.utils.R;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/9 下午4:37
 * @description: 搜索服务feign接口
 */
@FeignClient("store-search")
public interface SearchFeignService {

    @PostMapping("/search//product/save")
    R saveIndex(@RequestBody List<SkuEsModel> skuEsModelList);
}

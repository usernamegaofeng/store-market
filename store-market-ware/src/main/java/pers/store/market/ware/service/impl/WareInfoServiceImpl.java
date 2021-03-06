package pers.store.market.ware.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;

import pers.store.market.common.utils.R;
import pers.store.market.ware.dao.WareInfoDao;
import pers.store.market.ware.entity.WareInfoEntity;
import pers.store.market.ware.feign.MemberFeignService;
import pers.store.market.ware.service.WareInfoService;
import pers.store.market.ware.vo.FareVo;
import pers.store.market.ware.vo.MemberAddressVo;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Autowired
    private MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<WareInfoEntity>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.and(wrapper -> {
                wrapper.eq("id", key).or().like("name", key).or().like("address", key);
            });
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public FareVo getFare(Long addrId) {
        FareVo fareVo = new FareVo();
        R info = memberFeignService.info(addrId);
        if (info.getCode() == 0) {
            MemberAddressVo address = JSON.parseObject(JSON.toJSONString(info.get("memberReceiveAddress")),MemberAddressVo.class);
            fareVo.setAddress(address);
            String phone = address.getPhone();
            //取电话号的最后两位作为邮费,以作为测试
            String fare = phone.substring(phone.length() - 2, phone.length());
            fareVo.setFare(new BigDecimal(fare));
        }
        return fareVo;
    }
}
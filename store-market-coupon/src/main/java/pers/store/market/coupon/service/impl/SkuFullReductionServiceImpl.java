package pers.store.market.coupon.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.store.market.common.domain.dto.MemberPrice;
import pers.store.market.common.domain.dto.SkuReductionDto;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;

import pers.store.market.coupon.dao.SkuFullReductionDao;
import pers.store.market.coupon.entity.MemberPriceEntity;
import pers.store.market.coupon.entity.SkuFullReductionEntity;
import pers.store.market.coupon.entity.SkuLadderEntity;
import pers.store.market.coupon.service.MemberPriceService;
import pers.store.market.coupon.service.SkuFullReductionService;
import pers.store.market.coupon.service.SkuLadderService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    private SkuLadderService skuLadderService;
    @Autowired
    private MemberPriceService memberPriceService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 保存满减信息
     *
     * @param skuReductionDto 满减信息dto
     */
    @Override
    public void saveInfo(SkuReductionDto skuReductionDto) {
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuReductionDto.getSkuId());
        skuLadderEntity.setFullCount(skuReductionDto.getFullCount());
        skuLadderEntity.setDiscount(skuReductionDto.getDiscount());
        skuLadderEntity.setAddOther(skuReductionDto.getCountStatus());
        if (skuReductionDto.getFullCount() > 0) {
            skuLadderService.save(skuLadderEntity);
        }

        //2、sms_sku_full_reduction
        SkuFullReductionEntity reductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionDto, reductionEntity);
        if (reductionEntity.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
            this.save(reductionEntity);
        }
        //3、sms_member_price,过滤掉满减为0元的无效数据
        List<MemberPrice> memberPrice = skuReductionDto.getMemberPrice();
        List<MemberPriceEntity> collect = memberPrice.stream().map(item -> {
            MemberPriceEntity priceEntity = new MemberPriceEntity();
            priceEntity.setSkuId(skuReductionDto.getSkuId());
            priceEntity.setMemberLevelId(item.getId());
            priceEntity.setMemberLevelName(item.getName());
            priceEntity.setMemberPrice(item.getPrice());
            priceEntity.setAddOther(1);
            return priceEntity;
        }).filter(item -> item.getMemberPrice().compareTo(new BigDecimal("0")) == 1).collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }
}
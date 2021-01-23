package pers.store.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.store.market.ware.CouponServiceApplication;
import pers.store.market.ware.entity.CouponEntity;
import pers.store.market.ware.service.CouponService;

/**
 * @author Gaofeng
 * @date 2021/1/22 下午11:22
 * @description:
 */
@SpringBootTest(classes = CouponServiceApplication.class)
@RunWith(SpringRunner.class)
public class Test1 {

    @Autowired
    private CouponService couponService;

    @Test
    public void test() {
        CouponEntity byId = couponService.getById(1L);
        System.out.println(byId);
    }
}

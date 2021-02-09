package pers.store.market.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.store.market.coupon.WareServiceApplication;
import pers.store.market.coupon.entity.UndoLogEntity;
import pers.store.market.coupon.service.UndoLogService;


/**
 * @author Gaofeng
 * @date 2021/1/22 下午8:16
 * @description: 测试数据库中的blob字段与Java字段映射, 使用byte[]即可, 取出来转换
 */
@SpringBootTest(classes = WareServiceApplication.class)
@RunWith(SpringRunner.class)
public class UndoTest {

    @Autowired
    private UndoLogService undoLogService;

    @Test
    public void test() {
//        UndoLogEntity undoLogEntity = new UndoLogEntity();
//        undoLogEntity.setBranchId(1L);
//        undoLogEntity.setContext("aaa");
//        undoLogEntity.setExt("aaa");
//        undoLogEntity.setLogCreated(new Date());
//        undoLogEntity.setLogStatus(1);
//        undoLogEntity.setXid("111");
//        undoLogEntity.setLogModified(new Date());
//        undoLogEntity.setRollbackInfo("aaa".getBytes());
//        undoLogService.save(undoLogEntity);

        UndoLogEntity undoLogEntity = undoLogService.getById(1L);
        System.out.println(undoLogEntity);
        System.out.println(new String(undoLogEntity.getRollbackInfo()));
    }

}

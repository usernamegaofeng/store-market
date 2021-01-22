package pers.store.market.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.ware.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:46:28
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


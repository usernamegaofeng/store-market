package pers.store.market.common.domain.dto;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午6:03
 * @description: 用户上下文对象
 */
@Data
public class UserInfoContent {

    private Long userId;
    private String userKey;
    private boolean tempUser = false;  //浏览器是否已有user-key

}

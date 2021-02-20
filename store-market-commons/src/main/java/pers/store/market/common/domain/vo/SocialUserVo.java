package pers.store.market.common.domain.vo;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/2/20 上午10:49
 * @description: 社交登录参数vo
 */
@Data
public class SocialUserVo {

    /**
     * 令牌
     */
    private String access_token;

    private String remind_in;

    /**
     * 令牌过期时间
     */
    private long expires_in;

    /**
     * 该社交用户的唯一标识
     */
    private String uid;

    private String isRealName;
}

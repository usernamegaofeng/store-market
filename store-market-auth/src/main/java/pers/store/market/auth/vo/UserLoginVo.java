package pers.store.market.auth.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Gaofeng
 * @date 2021/2/19 下午3:51
 * @description: 登录vo
 */
@Data
public class UserLoginVo {

    @NotEmpty(message = "用户名不能为空")
    private String account;
    @NotEmpty(message = "密码不能为空")
    private String password;
}

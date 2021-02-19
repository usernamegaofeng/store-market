package pers.store.market.member.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Gaofeng
 * @date 2021/2/19 下午3:51
 * @description: 登录vo
 */
@Data
@ApiModel(value = "UserLoginVo", description = "用户登录vo")
public class UserLoginVo {

    private String account;
    private String password;
}

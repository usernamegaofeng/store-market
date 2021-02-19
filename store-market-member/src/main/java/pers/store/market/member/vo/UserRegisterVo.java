package pers.store.market.member.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author Gaofeng
 * @date 2021/2/19 下午3:50
 * @description: 注册vo
 */
@Data
@ApiModel(value = "UserRegisterVo", description = "用户注册参数vo")
public class UserRegisterVo {

    private String userName;
    private String password;
    private String phone;
}

package pers.store.market.common.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Gaofeng
 * @date 2021/2/20 上午11:03
 * @description:
 */
@Data
@ToString
public class MemberVo {

    private Long id;
    private Long levelId;
    private String username;
    private String password;
    private String nickname;
    private String mobile;
    private String email;
    private String header;
    private Integer gender;
    private Date birth;
    private String city;
    private String job;
    private String sign;
    private Integer sourceType;
    private Integer integration;
    private Integer growth;
    private Integer status;
    private Date createTime;
    private String accessToken;
    private String uid;
    private Long expiresIn;
}

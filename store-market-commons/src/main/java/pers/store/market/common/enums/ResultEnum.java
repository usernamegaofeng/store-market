package pers.store.market.common.enums;

import lombok.Getter;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 物流
 *  15: 会员
 *
 *
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "执行成功"),
    SYSTEM_ERROR(10000, "系统错误"),
    PARAM_ERROR(10001, "参数错误"),
    CREATE_FAILED(10002, "保存失败"),
    UPDATE_FAILED(10003, "更新失败"),
    DELETE_FAILED(10004, "删除失败"),
    QUERY_FAILED(10005, "查询失败"),
    //商品服务
    PRODUCT_UP_ERROR(11000, "上架失败"),
    //会员服务
    REPEAT_SEND_CODE(15000, "验证码获取频率太高，请稍后再试!"),
    USERNAME_EXIST(15001, "用户名已存在!"),
    PHONE_EXIST(15002, "电话号码已存在!"),
    USERNAME_OR_PASSWORD_ERROR(15003, "用户名或密码错误!");

    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}

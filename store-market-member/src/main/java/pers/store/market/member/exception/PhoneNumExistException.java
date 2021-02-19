package pers.store.market.member.exception;

/**
 * @author Gaofeng
 * @date 2021/2/19 下午7:33
 * @description:
 */
public class PhoneNumExistException extends RuntimeException {

    public PhoneNumExistException() {
        super("电话号码已存在!");
    }

}

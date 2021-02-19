package pers.store.market.member.exception;

/**
 * @author Gaofeng
 * @date 2021/2/19 下午7:32
 * @description:
 */
public class UserExistException extends RuntimeException {

    public UserExistException() {
        super("该用户已存在");
    }
}

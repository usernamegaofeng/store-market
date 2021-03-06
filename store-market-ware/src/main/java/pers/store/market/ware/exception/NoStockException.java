package pers.store.market.ware.exception;


/**
 * @author Gaofeng
 * @date 2021/3/5 下午8:42
 * @description:
 */
public class NoStockException extends RuntimeException{

    public NoStockException(String msg){
        super(msg);
    }
}

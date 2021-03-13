package pers.store.market.order.web;

import com.alipay.api.AlipayApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.store.market.order.service.OrderService;
import pers.store.market.order.template.AliPayTemplate;
import pers.store.market.order.vo.PayVo;

/**
 * @author Gaofeng
 * @date 2021/3/13 上午9:43
 * @description: 支付controller
 */
@Slf4j
@Controller
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AliPayTemplate aliPayTemplate;

    @ResponseBody
    @GetMapping(value = "/aliPayOrder",produces = "text/html")
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        PayVo payVo = orderService.aliPayOrder(orderSn);
        String pay = aliPayTemplate.pay(payVo);
        return pay;
    }
}

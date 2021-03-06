package pers.store.market.order.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pers.store.market.order.exception.NoStockException;
import pers.store.market.order.service.OrderService;
import pers.store.market.order.vo.OrderConfirmVo;
import pers.store.market.order.vo.OrderSubmitVo;
import pers.store.market.order.vo.SubmitOrderResponseVo;

/**
 * @author Gaofeng
 * @date 2021/2/27 下午4:13
 * @description: 订单服务页面跳转controller
 */
@Controller
public class OrderWebController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/confirm.html")
    public String confirm() {
        return "confirm";
    }

    @GetMapping("/pay.html")
    public String pay() {
        return "pay";
    }

    @GetMapping("/list.html")
    public String list() {
        return "list";
    }

    @GetMapping("/detail.html")
    public String detail() {
        return "detail";
    }

    /**
     * 去结算页功能
     *
     * @param model
     * @return
     */
    @GetMapping("/toTrade")
    public String confirmPage(Model model) {
        OrderConfirmVo orderConfirmVo = orderService.confirmOrders();
        model.addAttribute("confirmOrderData", orderConfirmVo);
        return "confirm";
    }

    /**
     * 下单功能
     */
    @PostMapping(value = "/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes attributes) {
        try {
            SubmitOrderResponseVo responseVo = orderService.submitOrder(vo);
            //下单成功来到支付选择页
            //下单失败回到订单确认页重新确定订单信息
            if (responseVo.getCode() == 0) {
                //成功
                model.addAttribute("order", responseVo.getOrder());
                return "pay";
            } else {
                String msg = "下单失败";
                switch (responseVo.getCode()) {
                    case 1:
                        msg += "令牌订单信息过期，请刷新再次提交!";
                        break;
                    case 2:
                        msg += "订单商品价格发生变化，请确认后再次提交!";
                        break;
                    case 3:
                        msg += "库存锁定失败，商品库存不足!";
                        break;
                }
                attributes.addAttribute("msg", msg);
                return "redirect:http://localhost:9200/toTrade";
            }
        } catch (Exception e) {
            if (e instanceof NoStockException) {
                String message = ((NoStockException) e).getMessage();
                attributes.addAttribute("msg", message);
            }
            return "redirect:http://localhost:9200/toTrade";
        }
    }

}

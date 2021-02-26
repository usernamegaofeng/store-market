package pers.store.market.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:33
 * @description: 购物车里面的购物项
 */
public class CartItemVo {

    /**
     * skuId
     */
    private Long skuId;
    /**
     * 是否被选中
     */
    private Boolean check = true;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品图片
     */
    private String image;
    /**
     * 商品套餐属性
     */
    private List<String> skuAttrValues;
    /**
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 商品数量
     */
    private Integer count;
    /**
     * 商品小计价格
     */
    private BigDecimal totalPrice;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSkuAttrValues() {
        return skuAttrValues;
    }

    public void setSkuAttrValues(List<String> skuAttrValues) {
        this.skuAttrValues = skuAttrValues;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 当前购物车项总价等于单价x数量
     *
     * @return
     */
    public BigDecimal getTotalPrice() {
        return price.multiply(new BigDecimal("" + count));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}

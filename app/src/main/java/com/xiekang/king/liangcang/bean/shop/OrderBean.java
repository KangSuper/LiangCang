package com.xiekang.king.liangcang.bean.shop;

/**
 * Created by King on 2016/9/9.
 */
public class OrderBean {
    private String show;
    private String order;
    private String orderParams;

    public OrderBean(String show, String order,String orderParams) {
        this.show = show;
        this.order = order;
        this.orderParams = orderParams;
    }

    public String getShow() {
        return show;
    }

    public String getOrder() {
        return order;
    }

    public String getOrderParams() {
        return orderParams;
    }
}

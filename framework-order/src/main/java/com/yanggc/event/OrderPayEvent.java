package com.yanggc.event;

import com.yanggc.pojo.Order;
import org.springframework.context.ApplicationEvent;

public class OrderPayEvent  extends ApplicationEvent {

    /**
     * 当前订单对象
     * @param source
     */
    private Order order;

    public OrderPayEvent(Object source) {
        super(source);
        this.order = (Order) source;
    }

    public Order getOrder() {
        return order;
    }

}

package org.springframework.data.mongodb.util;

import org.springframework.data.mongodb.entity.Order;

import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/3/4.
 */
public interface OrderOperations {

    List<Order> findOrdersByType(String t);
}

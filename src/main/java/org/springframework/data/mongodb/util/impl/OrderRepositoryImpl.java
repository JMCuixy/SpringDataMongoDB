package org.springframework.data.mongodb.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.entity.Order;
import org.springframework.data.mongodb.util.OrderOperations;

import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/3/4.
 */
public class OrderRepositoryImpl implements OrderOperations {

    @Autowired
    private MongoOperations mongoOperations;


    public List<Order> findOrdersByType(String t) {
        Query type = Query.query(Criteria.where("type").is(t));
        List<Order> orders = mongoOperations.find(type, Order.class);
        if (orders != null && orders.size()!=0){
            return orders;
        }
        return null;
    }
}

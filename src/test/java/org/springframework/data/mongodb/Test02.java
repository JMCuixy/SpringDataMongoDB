package org.springframework.data.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.entity.Order;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by XiuYin.Cui on 2018/3/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test02 {

    @Autowired
    private MongoOperations mongoOperations;

    /**
     * db.order.find({"client":"customer"})
     */
    @Test
    public void test01(){
        Criteria criteria = Criteria.where("client").is("customer");
        Query query = new Query(criteria);
        List<Order> orders = mongoOperations.find(query, Order.class);
        if (orders != null && orders.size() > 0){
            for (Order order : orders){
                System.out.println(order);
            }
        }
    }

    /**
     * db.order.find({"client":"customer","type":"豪华型"})
     * and
     */
    @Test
    public void test02(){
        Criteria criteria = Criteria.where("client").is("customer");
        criteria.and("type").is("豪华型");
        Query query = new Query(criteria);
        List<Order> orders = mongoOperations.find(query, Order.class);
        if (orders != null && orders.size() > 0){
            for (Order order : orders){
                System.out.println(order);
            }
        }
    }

    /**
     * db.order.find({"type":/^豪华/})
     * 正则表达式
     */
    @Test
    public void test03(){
        Criteria criteria = Criteria.where("client").is("customer");
        criteria.and("type").regex("^豪华");
        Query query = new Query(criteria);
        List<Order> orders = mongoOperations.find(query, Order.class);
        if (orders != null && orders.size() > 0){
            for (Order order : orders){
                System.out.println(order);
            }
        }
    }

    /**
     * db.order.find({"client":"customer"}).sort({"type":-1})
     * 排序
     */
    @Test
    public void test04(){
        Criteria criteria = Criteria.where("client").is("customer");
        Sort sort = new Sort(Sort.Direction.DESC,"type");
        Query query = new Query(criteria).with(sort);
        List<Order> orders = mongoOperations.find(query, Order.class);
        if (orders != null && orders.size() > 0){
            for (Order order : orders){
                System.out.println(order);
            }
        }
    }

    /**
     * db.order.find({"client":"customer"}).skip(5).limit(5)
     * 分页
     */
    @Test
    public void test05(){
        Criteria criteria = Criteria.where("client").is("customer");
        /*limit 是pageSize ， skip 是 第几页*pageSize */
        Query query = new Query(criteria).skip(5).limit(5);
        List<Order> orders = mongoOperations.find(query, Order.class);
        if (orders != null && orders.size() > 0){
            for (Order order : orders){
                System.out.println(order);
            }
        }
    }


    /**
     * 大于 小于 不等于
     */
    @Test
    public void test06(){
        Criteria criteria = Criteria.where("client").is("customer");
        criteria.and("key").lt(""); //小于
        criteria.and("key").lte(""); //小于等于
        criteria.and("key").gt(""); //大于
        criteria.and("key").gte(""); //大于等于
        criteria.and("key").ne(""); //不等于 mongoDB 没有 eq（等于） 这个操作
        Query query = new Query(criteria);
    }

    /**
     * $in $size $elemMatch $exists
     */
    @Test
    public void test07(){
        List<String> list = new ArrayList();
        Criteria condition = Criteria.where("x").lt(10).and("x").gt(5);
        Criteria criteria = Criteria.where("client").is("customer");
        criteria.and("key").in(list);
        criteria.and("key").size(3); //匹配key数组长度等于 3 的文档
        criteria.elemMatch(condition); //要求 x 的数组每个元素必须同时满足 大于5 小于10
        criteria.and("key").exists(true);
        Query query = new Query(criteria);
    }
}

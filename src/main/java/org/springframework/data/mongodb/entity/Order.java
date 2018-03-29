package org.springframework.data.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/3/1.
 */
@Document
public class Order {

    /**
     * @ID 生成MongoDB文档的_id 内容，如果不指定，MongoDB 会主动生成一个
     */
    @Id
    private String id;

    /**
     * @Field 映射成MongoDB文档的字段内容
     */
    @Field("client")
    private String customer;

    /**
     * @Indexed 是否在该字段上加上索引
     */
    @Indexed
    private String type;

    
    public String key;

    /**
     * 集合类型最好使用 ? 不确定类型(或者使用任意类型)
     * 否则会info（Found cycle for field 'itemList' in type 'Order' for path ''）
     * 表明你的代码中有潜在的循环使用
     */
    private List<?> itemList = new ArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<?> getItemList() {
        return itemList;
    }

    public void setItemList(List<?> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customer='" + customer + '\'' +
                ", type='" + type + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}

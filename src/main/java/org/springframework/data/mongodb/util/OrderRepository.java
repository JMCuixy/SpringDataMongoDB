package org.springframework.data.mongodb.util;

import org.springframework.data.mongodb.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/3/4.
 */
public interface OrderRepository extends MongoRepository<Order, String>,OrderOperations {
    /**
     * 根据customer从文档中获取Order集合
     * @param customer
     * @return
     */
    //@Query会接受一个JSON查询，而不是JPA查询。?0 表示第一个参数，?1 表示第二个参数，以此类推
    @Query("{'customer':?0,'type':'type'}")
    List<Order> findByCustomer(String customer);

    /**
     * 根据customer 和 type 从文档中获取Order集合
     * @param customer
     * @param type
     * @return
     */
    List<Order> findByCustomerAndType(String customer, String type);

    /**
     * 根据customer 和 type 从文档中获取Order集合（customer 在对比的时候使用的是like 而不是equals）
     * @param customer
     * @param type
     * @return
     */
    List<Order> findByCustomerLikeAndTypeLike(String customer, String type);

}

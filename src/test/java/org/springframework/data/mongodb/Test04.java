package org.springframework.data.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/4/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test04 {

    @Autowired
    private MongoOperations mongoOperations;

    /**
     * db.driverLocation.aggregate(
     *     {"$match":{"areaCode":"350203"}},
     *     {"$project":{"driverUuid":1,"uploadTime":1,"positionType":1}},
     *     {"$group":{"_id":{"driverUuid":"$driverUuid","positionType":"$positionType"},"uploadTime":{"$first":{"$year":"$uploadTime"}},"count":{"$sum":1}}},
     *     {"$sort":{"count":-1}},
     *     {"$limit":100},
     *     {"$skip":50}
     * )
     */
    @Test
    public void test04(){
        //match
        Criteria criteria = Criteria.where("350203").is("350203");
        AggregationOperation matchOperation = Aggregation.match(criteria);
        //project
        AggregationOperation projectionOperation = Aggregation.project("driverUuid", "uploadTime", "positionType");
        //group
        AggregationOperation groupOperation = Aggregation.group("driverUuid", "positionType")
                .first(DateOperators.dateOf("uploadTime").year()).as("uploadTime")
                .count().as("count");
        //sort
        Sort sort = new Sort(Sort.Direction.DESC, "count");
        AggregationOperation sortOperation = Aggregation.sort(sort);
        //limit
        AggregationOperation limitOperation = Aggregation.limit(100L);
        //skip
        AggregationOperation skipOperation = Aggregation.skip(50L);

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, projectionOperation, groupOperation, sortOperation, limitOperation, skipOperation);
        AggregationResults<Object> driverLocation = mongoOperations.aggregate(aggregation, "driverLocation", Object.class);
        List<Object> mappedResults = driverLocation.getMappedResults();

    }

}

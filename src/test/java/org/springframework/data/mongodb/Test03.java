package org.springframework.data.mongodb;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by XiuYin.Cui on 2018/4/13.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test03 {

    private static final double EARTH_RADIUS = 6378137d;

    @Autowired
    private MongoOperations mongoOperations;


    public void test01() {
        Criteria criteria = Criteria.where("driverUuid").is("d43bf3d759064ed69a914cb10a010e77");
        Query query = new Query(criteria);
        NearQuery nearQuery = NearQuery.near(118.194936, 24.491849)
                .distanceMultiplier(EARTH_RADIUS)
                .maxDistance(3000)
                .spherical(true)
                .query(query);
        GeoResults<Object> geoResults = mongoOperations.geoNear(nearQuery, Object.class);
        List<GeoResult<Object>> content = geoResults.getContent();

    }

}

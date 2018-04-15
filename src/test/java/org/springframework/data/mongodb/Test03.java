package org.springframework.data.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
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


    /**
     * $near 查询附近位置
     * db.runCommand({
     *    geoNear: "driverPoint" ,
     *    near: {"lng":118.193828,"lat":24.492242} ,
     *    spherical: true,
     *    maxDistance:10/3963.2,
     *    distanceMultiplier:3963.2,
     *    query:{"driverUuid":"d43bf3d759064ed69a914cb10a010e77"}
     * })
     */
    @Test
    public void nearTest() {
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

    /**
     * "$geoWithin"操作符找出完全包含在某个区域的文档？
     * db.driverPoint.find(
     *    {
     *      coordinate: {
     *        $geoIntersects: {
     *           $geometry: {
     *              type: "Polygon" ,
     *              coordinates: [
     *                [ [ 118.193828, 24.492242 ], [ 118.193953, 24.702114 ], [ 118.19387, 24.592242 ],[ 118.193828, 24.492242 ]]
     *              ]
     *           }
     *        }
     *      }
     *    }
     * )
     */
    @Test
    public void withInGeoJsonPolygonTest() {
        Point x = new Point(118.193828, 24.492242);
        Point y = new Point(118.193953, 24.702114);
        Point z = new Point(119.19387, 28.792242);
        Shape shape = new GeoJsonPolygon(x, y, z, x);
        Criteria criteria = Criteria.where("coordinate").within(shape);
        Query query = new Query(criteria);
        List<Object> objects = mongoOperations.find(query, Object.class);
    }


    /**
     * "$geoWithin"操作符找出矩形范围内的文档？
     * db.driverPoint.find(
     * {
     *   coordinate: {
     *      $geoWithin: {
     *         $box: [
     *           [ 118.0,24.0 ],
     *           [ 120.0,30.0 ]
     *         ]
     *      }
     *   }
     * }
     * )
     */
    public void withInBoxTest() {
        Point x = new Point(118.0, 24.0);
        Point y = new Point(120.0, 30.0);
        Shape shape = new Box(x, y);
        Criteria coordinate = Criteria.where("coordinate").within(shape);
        Query query = new Query(coordinate);
        List<Object> objects = mongoOperations.find(query, Object.class);
    }


    /**
     * $geoWithin"操作符找出圆形范围内的文档？
     * db.driverPoint.find(
     * {
     *   coordinate: {
     *      $geoWithin: {
     *          $center: [ [ 118.067678, 24.444373] , 10 ]
     *      }
     *   }
     * }
     * )
     */
    public void withInCircleTest() {
        Point point = new Point(118.067678, 24.444373);
        Distance distance = new Distance(10, Metrics.KILOMETERS);
        Shape shape = new Circle(point, distance);
        Criteria criteria = Criteria.where("coordinate").within(shape);
        Query query = new Query(criteria);
        List<Object> objects = mongoOperations.find(query, Object.class);
    }

    /**
     * "$geoWithin"操作符找出多边形范围内的文档？
     * db.driverPoint.find(
     * {
     *   coordinate: {
     *      $geoWithin: {
     *          $polygon: [ [ 118.067678 , 24.444373 ], [ 119.067678 , 25.444373 ], [ 120.067678 , 26.444373 ] ]
     *      }
     *   }
     * }
     * )
     */
    public void withInPolygonTest() {
        Point x = new Point(118.193828, 24.492242);
        Point y = new Point(118.193953, 24.702114);
        Point z = new Point(119.19387, 28.792242);
        Shape shape = new Polygon(x, y ,z);
        Criteria coordinate = Criteria.where("coordinate").within(shape);
        Query query = new Query(coordinate);
        mongoOperations.find(query, Object.class);
    }


    /**
     * $geoWithin"操作符找出球面圆形范围内的文档？
     *
     * db.driverPoint.find(
     * {
     *   coordinate: {
     *      $geoWithin: {
     *          $centerSphere: [ [ 118.067678, 24.444373 ], 10/3963.2 ]
     *      }
     *   }
     * }
     * )
     */
    public void withInCenterSphereTest(){
        Circle circle = new Circle(118.067678 , 24.444373 , 10/3963.2);
        Criteria criteria = Criteria.where("coordinate").withinSphere(circle);
        Query query = new Query(criteria);
        List<Object> objects = mongoOperations.find(query, Object.class);
    }


    /**
     * "$geoIntersects" 操作符找出与查询位置相交的文档 ？
     * db.driverPoint.find(
     *    {
     *      coordinate: {
     *        $geoIntersects: {
     *           $geometry: {
     *              type: "Polygon" ,
     *              coordinates: [
     *                [ [ 118.193828, 24.492242 ], [ 118.193953, 24.702114 ], [ 118.19387, 24.592242 ],[ 118.193828, 24.492242 ]]
     *              ]
     *           }
     *        }
     *      }
     *    }
     * )
     */
    public void withInTest(){
        Point x = new Point(118.193828, 24.492242);
        Point y = new Point(118.193953, 24.702114);
        Point z = new Point(119.19387, 28.792242);
        GeoJson geoJson = new  GeoJsonPolygon(x, y, z, x);
        Criteria coordinate = Criteria.where("coordinate").intersects(geoJson);
        Query query = new Query(coordinate);
        List<Object> objects = mongoOperations.find(query, Object.class);
    }
}

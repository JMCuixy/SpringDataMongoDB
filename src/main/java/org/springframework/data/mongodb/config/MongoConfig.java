package org.springframework.data.mongodb.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by XiuYin.Cui on 2018/3/1.
 */
@Configuration
@ComponentScan(basePackages = "org.springframework.data.mongodb")
@PropertySource("classpath:mongo.properties")
public class MongoConfig {

    @Bean(name = "mongo")
    public MongoClientFactoryBean mongoClientFactoryBean(Environment env) {
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        MongoClientOptions build = builder.build();
        MongoClientFactoryBean mongoClientFactoryBean = new MongoClientFactoryBean();
        mongoClientFactoryBean.setHost(env.getProperty("mongo.host", String.class));
        mongoClientFactoryBean.setPort(env.getProperty("mongo.port", Integer.class));
        mongoClientFactoryBean.setMongoClientOptions(build);
        return mongoClientFactoryBean;
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate(Mongo mongo, Environment env) {
        return new MongoTemplate(mongo, env.getProperty("mongo.dbname", String.class));
    }


}

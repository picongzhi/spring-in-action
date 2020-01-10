package com.pcz.orders.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author picongzhi
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.pcz.orders.db")
public class MongoConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "OrdersDB";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient();
    }
}

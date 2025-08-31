package click.dailyfeed.search.config;

import click.dailyfeed.search.config.config.BigDecimalToDecimal128Converter;
import click.dailyfeed.search.config.config.Decimal128ToBigDecimalConverter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Arrays;

@Configuration
public class MongoConfiguration {
    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.protocol}")
    private String protocol;

    @Value("${app.dailyfeed-search.mongodb.database}")
    private String database;

    @Bean
    public MongoClient mongoClient(){
        String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);
        String encodedPassword = URLEncoder.encode(password, StandardCharsets.UTF_8);
        String mongoUri = String.format("%s://%s:%s@%s:%d/%s",
                protocol, encodedUsername, encodedPassword, host, port, database);
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTransactionManager transactionManager(
            MongoDatabaseFactory dbFactory
    ){
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public SimpleMongoClientDatabaseFactory dailyfeedMongoDatabaseFactory(
            MongoClient mongoClient
    ){
        return new SimpleMongoClientDatabaseFactory(mongoClient, database);
    }

    @Bean
    public MongoTemplate dailyfeedMongoTemplate(
            MongoDatabaseFactory dailyfeedMongoDatabaseFactory,
            MongoConverter mongoConverter
    ){
        return new MongoTemplate(dailyfeedMongoDatabaseFactory, mongoConverter);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions(){
        return new MongoCustomConversions(
                Arrays.asList(
                        new BigDecimalToDecimal128Converter(),
                        new Decimal128ToBigDecimalConverter()
                )
        );
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory databaseFactory,
            MongoMappingContext mongoMappingContext
    ){
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
}

package com.example.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class DatabaseConfig {

	  @Bean
	  ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

	    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
	    initializer.setConnectionFactory(connectionFactory);
	    initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
	    initializer.afterPropertiesSet();
	    initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
	    initializer.setDatabaseCleaner(new ResourceDatabasePopulator(new ClassPathResource("clean.sql")));
    
	    return initializer;
	  }
	
}

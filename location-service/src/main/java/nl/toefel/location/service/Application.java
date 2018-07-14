package nl.toefel.location.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //https://docs.spring.io/spring-boot/docs/current/reference/html/howto-data-access.html#howto-configure-a-datasource
    //https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html

    //TODO disable createClob check https://vkuzel.com/spring-boot-jpa-hibernate-atomikos-postgresql-exception
    //TODO http://fabiomaffioletti.me/blog/2014/04/15/distributed-transactions-multiple-databases-spring-boot-spring-data-jpa-atomikos/

    @Bean
    public HikariDataSource dataSource() {
        Config cfg = Config.fromEnvironment();

        HikariConfig hikaryConfig = new HikariConfig();
        hikaryConfig.setJdbcUrl(cfg.getDatabaseUrl());
        hikaryConfig.setDriverClassName(cfg.getDatabaseDriverClassName());
        hikaryConfig.setUsername(cfg.getDatabaseUsername());
        hikaryConfig.setPassword(cfg.getDatabasePassword());

        return new HikariDataSource(hikaryConfig);
    }
}
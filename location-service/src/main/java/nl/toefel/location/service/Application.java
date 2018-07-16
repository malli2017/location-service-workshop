package nl.toefel.location.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.net.ConnectException;

@SpringBootApplication
@EnableRetry
public class Application {

    public static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(500);
        exponentialBackOffPolicy.setMaxInterval(1000);
        exponentialBackOffPolicy.setMultiplier(1.8);
        retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);

        return retryTemplate;
    }

    @Bean
    @Retryable(value= {ConnectException.class}, maxAttempts = 500)
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
package nl.toefel.location.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import nl.toefel.location.service.controller.VersionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.ConnectException;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;

@SpringBootApplication
@EnableRetry
@EnableSwagger2
public class Application {

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
    @Retryable(value = {ConnectException.class}, maxAttempts = 500)
    public HikariDataSource dataSource() {
        Config cfg = Config.fromEnvironment();

        HikariConfig hikariCfg = new HikariConfig();
        hikariCfg.setJdbcUrl(cfg.getDatabaseUrl());
        hikariCfg.setDriverClassName(cfg.getDatabaseDriverClassName());
        hikariCfg.setUsername(cfg.getDatabaseUsername());
        hikariCfg.setPassword(cfg.getDatabasePassword());

        return new HikariDataSource(hikariCfg);
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(VersionController.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .protocols(newHashSet("http"))
                .genericModelSubstitutes(Optional.class);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(source);
    }

}
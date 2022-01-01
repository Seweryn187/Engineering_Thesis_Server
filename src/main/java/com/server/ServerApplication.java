package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication(scanBasePackages = {"com.server", "com.server.repositories", "com.server.entities"
        ,"com.server.services", "com.server.controllers"})
@EnableJpaRepositories("com.server.repositories")
@EntityScan("com/server/entities")
@EnableAsync
@Configuration
@ComponentScan(basePackages = {"com.server.services", "com.server.controllers", "com.server.repositories", "com.server.data", "com.server.alerts", "com.server.utility"})
public class ServerApplication implements AsyncConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("async executor-");
        executor.initialize();
        return executor;
    }
}

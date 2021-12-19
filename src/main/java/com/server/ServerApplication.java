package com.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import repositories.CurrencyRepository;
import repositories.CurrentValueRepository;

@SpringBootApplication(scanBasePackages = {"com.server", "services", "repositories", "entities"})
@EnableJpaRepositories("repositories")
@EntityScan("entities")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CurrencyRepository repository, CurrentValueRepository repository2) {
        return (args) -> {
            System.out.println(repository.findAll());
            System.out.println(repository2.findAll());
        };
    }
}

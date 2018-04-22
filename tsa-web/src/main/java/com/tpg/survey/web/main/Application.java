package com.tpg.survey.web.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author amit.bharti
 *
 */
@EnableJpaRepositories(basePackages = "com.tpg.survey.repository")
@EntityScan(basePackages = "com.tpg.survey.domain")
@ComponentScan(basePackages = "com.tpg")
@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

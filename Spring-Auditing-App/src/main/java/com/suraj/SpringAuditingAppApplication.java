package com.suraj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.suraj.config.AuditorConfig;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "awareRef")
public class SpringAuditingAppApplication {

	@Bean
	public AuditorAware<Integer> awareRef()
	{
		return new AuditorConfig();
	}
	
	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringAuditingAppApplication.class, args);
	}

}

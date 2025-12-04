package com.mmdev.meowmayo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeowMayoWsApplication {
	public static void main(String[] args) {
		SpringApplication.run(MeowMayoWsApplication.class, args);
	}
}

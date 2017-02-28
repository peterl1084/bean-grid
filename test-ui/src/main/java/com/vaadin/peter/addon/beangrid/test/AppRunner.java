package com.vaadin.peter.addon.beangrid.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.vaadin.peter.addon.beangrid.EnableBeanGrid;

@SpringBootApplication
@EnableBeanGrid
public class AppRunner extends SpringBootServletInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(AppRunner.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(AppRunner.class);
	}
}

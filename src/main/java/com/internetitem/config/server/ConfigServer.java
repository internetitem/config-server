package com.internetitem.config.server;

import com.internetitem.config.server.dao.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@EnableAutoConfiguration
@ComponentScan
@Configuration
public class ConfigServer extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(ConfigServer.class, args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ConfigServer.class);
	}

	@Autowired
	private DatabaseAccess db;

	@PostConstruct
	public void startup() throws Exception {
		db.upgrade();
	}

}

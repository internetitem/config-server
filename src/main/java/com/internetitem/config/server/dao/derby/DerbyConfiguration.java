package com.internetitem.config.server.dao.derby;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ConditionalOnProperty("derby.url")
public class DerbyConfiguration {

	@Value("${derby.embedded:false}")
	private boolean embedded;

	@Value("${derby.url}")
	private String url;

	@PostConstruct
	public void init() throws Exception {
		if (embedded) {
			Properties props = new Properties();
			props.put("create", "true");
			DriverManager.getConnection(url, props).close();
		}
	}

	@PreDestroy
	public void destroy() {
		if (embedded) {
			Properties props = new Properties();
			props.put("drop", "true");
			try {
				DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				// Ignore
			}
		}
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		dmds.setUrl(url);
		return dmds;
	}

	@Bean
	public NamedParameterJdbcTemplate getJdbcTemplate(DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}

}

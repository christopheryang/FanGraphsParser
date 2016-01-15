package com.fantasyworks.fangraphsparser.app;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Christopher Yang
 */
@Configuration
@Profile("dev")
public class DevConfig {

	/**
	 * MySQL data source for dev. No need for a connection pool.
	 * @return
	 */
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/reconciliation_tool_payplus?useServerPrepStmts=false&rewriteBatchedStatements=true");
		dataSource.setUsername("bart_dev");
		dataSource.setPassword("secret");
		return dataSource;
	}

}

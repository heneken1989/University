package com.aptech.group3.Config;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class ConfigDatabase {
	@Value("${db.datasource.driverClassName}")
	private String driverClassName;

	@Value("${db.datasource.url}")
	private String urlDB;

	@Value("${db.datasource.username}")
	private String userName;

	@Value("${db.datasource.password}")
	private String password;

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(urlDB);
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setUsername(userName);
		hikariConfig.setPassword(password);

		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		return hikariDataSource;
	}

	@Bean(name = "transactionManager")
	DataSourceTransactionManager dataSourceTransactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	
	/*
	 * @Bean(name = "entityManagerFactory") public
	 * LocalContainerEntityManagerFactoryBean entityManagerFactory(
	 * EntityManagerFactoryBuilder builder, DataSource dataSource) { return builder
	 * .dataSource(dataSource) .packages("com.aptech.group3.entity") // Package
	 * containing your entity classes .persistenceUnit("default") .build(); }
	 */
    
	
	
	

	
	/*
	 * @Bean(name="sqlSessionFactory") public
	 * sessionFactory(@Qualifier("dataSource") DataSource dataSource) throws
	 * Exception { SqlSessionFactoryBean sqlSessionFactoryBean= new
	 * SqlSessionFactoryBean(); sqlSessionFactoryBean.setDataSource(dataSource);
	 * return sqlSessionFactoryBean.getObject();
	 * 
	 * }
	 */
	 
}
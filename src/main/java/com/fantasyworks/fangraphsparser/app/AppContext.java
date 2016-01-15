package com.fantasyworks.fangraphsparser.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages="com.livegamer")
@EnableJpaRepositories("com.livegamer.financetool.repo")
@EnableTransactionManagement
@EnableSpringDataWebSupport
public class AppContext extends WebMvcConfigurerAdapter {

	// One of the environment-specific configurations
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * Entity manager factory
	 * 
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPersistenceUnitName("default");
		em.setPackagesToScan(new String[] {"com.livegamer.financetool.entity"});

		em.setJpaVendorAdapter(jpaVenderAdaptor());
		em.setJpaProperties(additionalProperties());

		return em;
	}

	/**
	 * Hibernate JPA vendor adapter. This class has limited configurations for Hibernate.
	 * @return
	 */
	@Bean
	public JpaVendorAdapter jpaVenderAdaptor(){
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(false);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		vendorAdapter.setGenerateDdl(false);
		return vendorAdapter;
	}

	/**
	 * Hibernate specific configurations. See https://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html/ch03.html.
	 * @return
	 */
	Properties additionalProperties() {
		Properties properties = new Properties();
		//properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		//properties.setProperty("hibernate.hbm2ddl.auto", "validate");
		properties.setProperty("hibernate.format_sql", "false");
		
		// For batch insert/update
		properties.setProperty("hibernate.jdbc.batch_size", "100");
		properties.setProperty("hibernate.order_inserts", "true");
		properties.setProperty("hibernate.order_updates", "true");
		properties.setProperty("hibernate.jdbc.batch_versioned_data", "true");
		properties.setProperty("hibernate.generate_statistics", "false");
		properties.setProperty("hibernate.use_sql_comments", "false");
		return properties;
	}

	/**
	 * 
	 * @param emf
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	/**
	 * Add custom message converters
	 */
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jsonConverter());
 
        super.configureMessageConverters(converters);
    }
	
	/**
	 * Used by Error Handler and other REST services.
	 * 
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter jsonConverter(){
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(jacksonObjectMapper());
		return converter;
	}
	
	@Bean
	public ObjectMapper jacksonObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		
		DateFormat utcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		objectMapper.setDateFormat(utcDateFormat);
		
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		
		return objectMapper;
	}
	
}

package com.more.sdk.config;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages={"com.more.sdk.action","com.more.sdk.service","com.more.sdk.entity.dao"})
@PropertySource("classpath:config.properties")
@EnableTransactionManagement
public class SpringConfig {
	@Bean
    public BasicDataSource dataSource() {
		try {
			Context initialContext = new InitialContext();
			BasicDataSource datasource = (BasicDataSource)initialContext.lookup("java:comp/env/jdbc/more");
			return datasource;
		} catch (NamingException e) {
			return null;
		}
    }
	
	@Bean
    public SqlSessionFactoryBean sessionFactory() throws IOException {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/more/sdk/entity/mapper/*.xml"));
        return sessionFactory;
     }
	
	
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage("com.more.sdk.entity.dao");
		configurer.setSqlSessionFactoryBeanName("sessionFactory");
		return configurer;
	}
	
	@Bean
    @Autowired
    public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());
       return txManager;
    }
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	/*@Resource
	private Environment environment;
	
	
	
	@Bean
    @Autowired
    public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());
       return txManager;
    }*/
}

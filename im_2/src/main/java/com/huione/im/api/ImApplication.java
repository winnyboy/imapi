package com.huione.im.api;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.huione.im.api.interceptor.ApiInterceptor;



@SpringBootApplication
@MapperScan("com.huione.im.api.dao")
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true,encoding = "UTF-8") 
public class ImApplication extends WebMvcConfigurerAdapter {

	@Autowired
	private ApiInterceptor apiInterceptor;
	
    public static void main(String[] args) {
        SpringApplication.run(ImApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
    	return  DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
    	SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolve = resolver();
        MybatisProperties  mybatisProperties = this.mybatisProperties();
        sqlSessionFactoryBean.setConfigLocation(resolve.getResource(mybatisProperties.getConfigLocation()));
        sqlSessionFactoryBean.setMapperLocations(resolve.getResources(mybatisProperties.getMapperLocations()[0]));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "mybatis")
    public MybatisProperties mybatisProperties() {
        return new MybatisProperties();
    }

    @Bean
    public PathMatchingResourcePatternResolver resolver(){
    	return new PathMatchingResourcePatternResolver();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
    	return new DataSourceTransactionManager(dataSource());
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(apiInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
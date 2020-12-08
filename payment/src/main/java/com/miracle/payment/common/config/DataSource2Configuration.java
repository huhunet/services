package com.miracle.payment.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@MapperScan(basePackages = "",sqlSessionFactoryRef = "sqlSessionFactory2")
public class DataSource2Configuration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.data2")
    public DataSource dataSource2() throws SQLException {
        DataSource dataSource = DataSourceBuilder.create().build();
        dataSource.setLoginTimeout(5000);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory2() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource2());
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate2() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory2());
    }

    @Bean
    public DataSourceTransactionManager transactionManager2() throws SQLException {
        return new DataSourceTransactionManager(dataSource2());
    }
}

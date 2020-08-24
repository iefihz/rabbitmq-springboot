package com.iefihz.reliabledelivery.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * SpringBoot没有整合DruidDataSource，需要个人配置
 */
@Configuration
@MapperScan(basePackages = DruidDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
public class DruidDataSourceConfig {

    /**
     * XxxMapper.xml文件路径
     */
    public static final String MAPPER_LOCATIONS = "mapper/*.xml";

    /**
     * Mapper接口包路径
     */
    public static final String PACKAGE = "com.iefihz.reliabledelivery.dao";

    /**
     * 该包下的实体类，在mybatis的xml中可以使用别名
     */
    public static final String TYPE_ALIASES_PACKAGE = "com.iefihz.reliabledelivery.entity";

    /**
     * 将yml中前缀为spring.datasource相关的DruidDataSource配置在类初始化时注入进来
     * @return 数据源
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory druidSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(DruidDataSourceConfig.TYPE_ALIASES_PACKAGE);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DruidDataSourceConfig.MAPPER_LOCATIONS));
        return sessionFactory.getObject();
    }
}

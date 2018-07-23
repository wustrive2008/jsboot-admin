package com.wubaoguo.springboot.conf.multidatasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 数据源配置
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/7/23 16:35
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Configuration
@MapperScan("com.wubaoguo.springboot.dao")
@EnableTransactionManagement
@Log4j2
public class DataSourceConfig {

    @Autowired
    private Environment env;  // (1)

    @Autowired
    private DataSourceProperties properties;  // (2)

    @Value("${spring.datasource.druid.filters}")   // (3)
    private String filters;

    @Value("${spring.datasource.druid.initial-size}")
    private Integer initialSize;

    @Value("${spring.datasource.druid.min-idle}")
    private Integer minIdle;

    @Value("${spring.datasource.druid.max-active}")
    private Integer maxActive;

    @Value("${spring.datasource.druid.max-wait}")
    private Integer maxWait;

    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
    private Long timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.min-evictable-idle-time-millis}")
    private Long minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.druid.test-while-idle}")
    private Boolean testWhileIdle;

    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.test-on-return}")
    private boolean testOnReturn;

    @Value("${spring.datasource.druid.pool-prepared-statements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size}")
    private Integer maxPoolPreparedStatementPerConnectionSize;


    /**
     * 通过Spring JDBC 快速创建 DataSource
     *
     * @return
     */
    @Bean(name = "masterDataSource")
    @Qualifier("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")  // (4)
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * 手动创建DruidDataSource,通过DataSourceProperties 读取配置
     *
     * @return
     * @throws SQLException
     */
    @Bean(name = "slaveDataSource")
    @Qualifier("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setFilters(filters);
        dataSource.setUrl(properties.getUrl());
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        return dataSource;
    }

    /**
     * 构造多数据源连接池
     * Master 数据源连接池采用 HikariDataSource
     * Slave  数据源连接池采用 DruidDataSource
     *
     * @param master
     * @param slave
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource master,
                                        @Qualifier("slaveDataSource") DataSource slave) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.master, master);
        targetDataSources.put(DatabaseType.slave, slave);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(slave);// 默认的datasource设置为myTestDbDataSource

        String read = env.getProperty("spring.datasource.read");
        dataSource.setMethodType(DatabaseType.slave, read);

        String write = env.getProperty("spring.datasource.write");
        dataSource.setMethodType(DatabaseType.master, write);

        return dataSource;
    }

    // 处理jdbcTemplate 数据源问题

    @Bean
    public JdbcTemplate jdbcTemplate(
            @Qualifier("masterDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(
            @Qualifier("masterDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource myTestDbDataSource,
                                               @Qualifier("slaveDataSource") DataSource myTestDb2DataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(this.dataSource(myTestDbDataSource, myTestDb2DataSource));
        fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
        return fb.getObject();
    }


    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}

package org.linfa.micro.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class MybatisConfiguration implements EnvironmentAware {
    private Binder binder;
    private String driveClassName;
    private String url;
    private String userName;
    private String password;
    private String xmlLocation;
    private String typeAliasesPackage;
    /////////////////////druid参数///////////////////////////////////////////////////
    private String filters;
    private String maxActive;
    private String initialSize;
    private String maxWait;
    private String minIdle;
    private String timeBetweenEvictionRunsMillis;
    private String minEvictableIdleTimeMillis;
    private String validationQuery;
    private String testWhileIdle;
    private String testOnBorrow;
    private String testOnReturn;
    private String poolPreparedStatements;
    private String maxOpenPreparedStatements;
    //////////////////////////////////////////////////////////////////////////

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(StringUtils.isNotBlank(driveClassName)?driveClassName:"com.mysql.jdbc.Driver");
        druidDataSource.setMaxActive(StringUtils.isNotBlank(maxActive)? Integer.parseInt(maxActive):10);
        druidDataSource.setInitialSize(StringUtils.isNotBlank(initialSize)? Integer.parseInt(initialSize):1);
        druidDataSource.setMaxWait(StringUtils.isNotBlank(maxWait)? Integer.parseInt(maxWait):60000);
        druidDataSource.setMinIdle(StringUtils.isNotBlank(minIdle)? Integer.parseInt(minIdle):3);
        druidDataSource.setTimeBetweenEvictionRunsMillis(StringUtils.isNotBlank(timeBetweenEvictionRunsMillis)?
                Integer.parseInt(timeBetweenEvictionRunsMillis):60000);
        druidDataSource.setMinEvictableIdleTimeMillis(StringUtils.isNotBlank(minEvictableIdleTimeMillis)?
                Integer.parseInt(minEvictableIdleTimeMillis):300000);
        druidDataSource.setValidationQuery(StringUtils.isNotBlank(validationQuery)?validationQuery:"select 'x'");
        druidDataSource.setTestWhileIdle(StringUtils.isNotBlank(testWhileIdle)? Boolean.parseBoolean(testWhileIdle):true);
        druidDataSource.setTestOnBorrow(StringUtils.isNotBlank(testOnBorrow)? Boolean.parseBoolean(testOnBorrow):false);
        druidDataSource.setTestOnReturn(StringUtils.isNotBlank(testOnReturn)? Boolean.parseBoolean(testOnReturn):false);
        druidDataSource.setPoolPreparedStatements(StringUtils.isNotBlank(poolPreparedStatements)? Boolean.parseBoolean(poolPreparedStatements):true);
        druidDataSource.setMaxOpenPreparedStatements(StringUtils.isNotBlank(maxOpenPreparedStatements)? Integer.parseInt(maxOpenPreparedStatements):20);

        try {
            druidDataSource.setFilters(StringUtils.isNotBlank(filters)?filters:"stat, wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        if(StringUtils.isNotBlank(typeAliasesPackage)){
            bean.setTypeAliasesPackage(typeAliasesPackage);
        }
        //分页插件
//        PageHelper pageHelper = new PageHelper();
        PageInterceptor pageInterceptor=new PageInterceptor();

        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageInterceptor.setProperties(properties);
//        pageHelper.setProperties(properties);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Interceptor[] plugins =  new Interceptor[]{pageInterceptor};
//        plugins.
        bean.setPlugins(plugins);
        try {
            bean.setMapperLocations(resolver.getResources(xmlLocation));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.binder=Binder.get(environment);
        this.url = binder.bind("spring.datasource.url",String.class).get();
        this.userName= binder.bind("spring.datasource.username",String.class).get();
        this.password = binder.bind("spring.datasource.password",String.class).get();
        this.driveClassName =binder.bind("spring.datasource.driver-class-name",String.class).get();
        this.filters = binder.bind("spring.datasource.filters",String.class).get();
        this.maxActive = binder.bind("spring.datasource.maxActive",String.class).get();
        this.initialSize = binder.bind("spring.datasource.initialSize",String.class).get();
        this.maxWait = binder.bind("spring.datasource.maxWait",String.class).get();
        this.minIdle = binder.bind("spring.datasource.minIdle",String.class).get();
        this.timeBetweenEvictionRunsMillis = binder.bind("spring.datasource.timeBetweenEvictionRunsMillis",String.class).get();
        this.minEvictableIdleTimeMillis = binder.bind("spring.datasource.minEvictableIdleTimeMillis",String.class).get();
        this.validationQuery = binder.bind("spring.datasource.validationQuery",String.class).get();
        this.testWhileIdle = binder.bind("spring.datasource.testWhileIdle",String.class).get();
        this.testOnBorrow = binder.bind("spring.datasource.testOnBorrow",String.class).get();
        this.testOnReturn = binder.bind("spring.datasource.testOnReturn",String.class).get();
        this.poolPreparedStatements = binder.bind("spring.datasource.poolPreparedStatements",String.class).get();
        this.maxOpenPreparedStatements = binder.bind("spring.datasource.maxOpenPreparedStatements",String.class).get();
        this.typeAliasesPackage = binder.bind("mybatis.typeAliasesPackage",String.class).get();
        this.xmlLocation = binder.bind("mybatis.xmlLocation",String.class).get();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}

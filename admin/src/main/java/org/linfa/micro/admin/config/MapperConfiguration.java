package org.linfa.micro.admin.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * mybatis mapper 扫描配置类
 */
@Configuration
@AutoConfigureAfter(MybatisConfiguration.class)
public class MapperConfiguration implements EnvironmentAware{
//    private RelaxedPropertyResolver propertyResolver;
    private Binder binder;
    private String basePackage;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(Environment environment){

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(basePackage);
        return mapperScannerConfigurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        binder=Binder.get(environment);
        this.basePackage=binder.bind("mybatis.basepackage",String.class).get();
    }
}

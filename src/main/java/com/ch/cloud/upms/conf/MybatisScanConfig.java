package com.ch.cloud.upms.conf;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * desc:MyBatis扫描接口
 * 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
 *
 * @author zhimin
 * @date 2018/12/22 12:33 AM
 */
@Configuration
@AutoConfigureAfter(MybatisConfig.class)
public class MybatisScanConfig {


    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");

        StringBuffer packages = new StringBuffer();
        packages.append("com.ch.cloud.upms.**.mapper");

        mapperScannerConfigurer.setBasePackage(packages.toString());
        Properties properties = new Properties();
        properties.setProperty("mappers", "tk.mybatis.mapper.common.Mapper,tk.mybatis.mapper.common.special.InsertListMapper");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }

}

package com.ch.cloud.upms;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEncryptableProperties
@EnableFeignClients
public class UpmsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UpmsApplication.class, args);
    }

}


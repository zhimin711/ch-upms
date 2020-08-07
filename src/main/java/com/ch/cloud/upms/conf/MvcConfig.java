package com.ch.cloud.upms.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * desc:
 *
 * @author zhimin
 * @date 2018/12/21 11:33 PM
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.ch.cloud"})
public class MvcConfig implements WebMvcConfigurer {

    @Value("${path.upload:}")
    private String uploadPath;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/static/");
        }
        if (!registry.hasMappingForPattern("/upload/ue/**")) {
            registry.addResourceHandler("/upload/ue/**")
                    .addResourceLocations("file:" + uploadPath + File.separator + "ue" + File.separator);
        }
    }

}

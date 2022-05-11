package com.ch.cloud.upms.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
/*

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }*/

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

    @Bean
    public RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(20000);
        factory.setConnectTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(factory);
        // 找出并修改默认的StringHttpMessageConverter
        // 关闭Accept-Charset的输出&#xff08;防止输出超长的编码列表&#xff09;
        // 设置默认编码为UTF-8
        boolean stringConverterFound = false;
        for (HttpMessageConverter<?> httpMessageConverter : restTemplate.getMessageConverters()) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter)httpMessageConverter;
                stringHttpMessageConverter.setWriteAcceptCharset(false);
                stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
                stringConverterFound = true;
                break;
            }
        }
        if (!stringConverterFound) {
            // 如果不存在StringHttpMessageC onverter&#xff0c;则创建一个
            StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
            stringHttpMessageConverter.setWriteAcceptCharset(false);
            restTemplate.getMessageConverters().add(stringHttpMessageConverter);
        }
        return restTemplate;
    }
    // 创建用于重试的retryTemplate
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(new ExponentialBackOffPolicy());
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);//配置的重试次数
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}

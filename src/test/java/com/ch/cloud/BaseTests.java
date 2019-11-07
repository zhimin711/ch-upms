package com.ch.cloud;

import com.ch.utils.CharUtils;
import com.ch.utils.CommonUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 01370603
 */
public class BaseTests {

    @Test
    public void pwd(){
        System.out.println(new BCryptPasswordEncoder().encode("secret"));
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }


    @Test
    public void testCharter(){
        System.out.println(CommonUtils.isNumeric("123"));
        System.out.println(CharUtils.isChinese("adsf"));
        System.out.println(CharUtils.isChinese("为c城"));
        System.out.println(CharUtils.containsChinese("co为"));
        System.out.println(CharUtils.isChineseByCJK("工"));
    }
}

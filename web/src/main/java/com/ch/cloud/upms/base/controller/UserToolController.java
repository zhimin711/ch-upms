package com.ch.cloud.upms.base.controller;

import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理
 *
 * @author 01370603
 * @since 2018/12/22 22:35
 */

@RestController
@RequestMapping("tools")
@Slf4j
public class UserToolController {

    private StringEncryptor stringEncryptor;

    @GetMapping("jasypt/encrypt")
    public Result<String> encrypt(@RequestParam("password") String password, @RequestParam("plainText") String plainText) {
        return ResultUtils.wrapFail(() -> {
            System.setProperty("jasypt.encryptor.password", password);
            stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
            return stringEncryptor.encrypt(plainText);
        });
    }

    @GetMapping("jasypt/decrypt")
    public Result<String> decrypt(@RequestParam("password") String password, @RequestParam("encryptText") String encryptText) {
        return ResultUtils.wrapFail(() -> {
            System.setProperty("jasypt.encryptor.password", password);
            stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
            return stringEncryptor.decrypt(encryptText);
        });
    }

}

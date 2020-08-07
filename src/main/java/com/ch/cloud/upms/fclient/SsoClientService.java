package com.ch.cloud.upms.fclient;

import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ch-sso")
public interface SsoClientService {

    @PostMapping("encrypt")
    Result<String> encrypt(@RequestParam("str") String password);

    @PostMapping("matchEncrypt")
    Result<Boolean> matchEncrypt(@RequestParam("str") String password, @RequestParam("encodedStr") String encodedStr);

}

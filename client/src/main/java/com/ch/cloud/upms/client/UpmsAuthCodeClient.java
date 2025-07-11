package com.ch.cloud.upms.client;

import com.ch.cloud.upms.dto.AuthCodeGenerateDTO;
import com.ch.cloud.upms.dto.AuthCodePermissionDTO;
import com.ch.cloud.upms.dto.AuthCodeResourceDTO;
import com.ch.cloud.upms.dto.AuthCodeVO;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * decs:通用用户权限代码获取服务
 *
 * @author zhimin
 * @since 2025/6/28
 */

@FeignClient(name = "${feign.client.upms:ch-upms}", url = "${feign.client.upmsUrl:}", path = "/fc/auth", contextId = "upmsAuthCodeClient")
public interface UpmsAuthCodeClient {
    
    @GetMapping({"generate"})
    Result<AuthCodeVO> generate(@RequestBody AuthCodeGenerateDTO dto);
    
    @GetMapping({"{code}"})
    Result<AuthCodePermissionDTO> getPermission(@PathVariable String code);
    
    @GetMapping({"{code}/content"})
    Result<AuthCodeResourceDTO> getContent(@PathVariable String code);
}

package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.cloud.upms.base.mapper.AuthCodeMapper;
import com.ch.cloud.upms.base.model.AuthCode;
import com.ch.cloud.upms.service.IAuthCodeService;
import org.springframework.stereotype.Service;

@Service
public class AuthCodeServiceImpl extends ServiceImpl<AuthCodeMapper, AuthCode> implements IAuthCodeService {
    
    // 可扩展自定义业务实现
} 
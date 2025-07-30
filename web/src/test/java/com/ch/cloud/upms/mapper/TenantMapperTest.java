package com.ch.cloud.upms.mapper;

import com.ch.cloud.upms.user.mapper.UserMapper;
import com.ch.cloud.upms.user.model.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TenantMapper测试类
 *
 * @author zhimin
 * @since 2024/1/1
 */
@SpringBootTest
public class TenantMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindTenantsByUsername() {
        // 测试查询租户列表
        String username = "admin"; // 假设存在的用户名
        List<Tenant> tenants = userMapper.findTenantsByUsername(username);
        
        // 验证查询结果
        assertNotNull(tenants, "租户列表不应该为null");
        System.out.println("查询到 " + tenants.size() + " 个租户");
        
        for (Tenant tenant : tenants) {
            // 验证基本字段
            assertNotNull(tenant.getId(), "租户ID不应该为null");
            assertNotNull(tenant.getName(), "租户名称不应该为null");
            assertNotNull(tenant.getDepartmentId(), "部门ID不应该为null");
            
            // 验证manager字段
            System.out.println("租户: " + tenant.getName() + 
                             ", ID: " + tenant.getId() + 
                             ", Manager: " + tenant.getManager() + 
                             ", Manager类型: " + (tenant.getManager() != null ? tenant.getManager().getClass().getName() : "null"));
            
            assertNotNull(tenant.getManager(), "manager字段不应该为null");
            // manager字段应该是List<UsernameDTO>类型，即使是空列表也不应该是null
            
            if (tenant.getManager() != null && !tenant.getManager().isEmpty()) {
                for (Object manager : tenant.getManager()) {
                    System.out.println("  Manager详情: " + manager);
                }
            }
        }
    }
} 
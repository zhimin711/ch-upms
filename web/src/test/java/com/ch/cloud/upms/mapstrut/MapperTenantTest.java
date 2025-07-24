package com.ch.cloud.upms.mapstrut;

import com.ch.cloud.upms.dto.TenantDto;
import com.ch.cloud.upms.user.model.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MapperTenant转换器测试类
 *
 * @author zhimin
 * @since 2024/1/1
 */
@SpringBootTest
public class MapperTenantTest {

    @Test
    public void testToClientDto() {
        // 创建测试数据
        Tenant tenant = new Tenant();
        tenant.setId(1L);
        tenant.setName("测试租户");
        tenant.setDepartmentId("dept001");
        tenant.setDepartmentName("测试部门");
        tenant.setManager("张三");
        tenant.setSort(1);
        tenant.setDescription("这是一个测试租户");
        tenant.setStatus("1");
        tenant.setDeleted(false);

        // 执行转换
        TenantDto dto = MapperTenant.INSTANCE.toClientDto(tenant);

        // 验证转换结果
        assertNotNull(dto);
        assertEquals(tenant.getId(), dto.getId());
        assertEquals(tenant.getName(), dto.getName());
        assertEquals(tenant.getDepartmentId(), dto.getDeptId());
    }

    @Test
    public void testToClientDtoWithNullValues() {
        // 创建包含空值的测试数据
        Tenant tenant = new Tenant();
        tenant.setId(2L);
        // name和departmentId为null

        // 执行转换
        TenantDto dto = MapperTenant.INSTANCE.toClientDto(tenant);

        // 验证转换结果
        assertNotNull(dto);
        assertEquals(tenant.getId(), dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getDeptId());
    }
} 
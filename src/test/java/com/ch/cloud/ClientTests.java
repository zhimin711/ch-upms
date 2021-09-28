package com.ch.cloud;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ch.cloud.client.UpmsClientService;
import com.ch.cloud.upms.UpmsApplication;
import com.ch.cloud.upms.pojo.NacosNamespace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = UpmsApplication.class)
public class ClientTests {

    //	@Autowired
    UpmsClientService upmsClientService;

    Object o;

    //	@Test
    public void contextLoads() {

//		o = upmsClientService.findPermissionsByRoleId(0L);
        o = upmsClientService.findMenusByRoleId(0L);

        System.out.println(o);
    }

    @Test
    public void namespaces() {

        JSONObject resp = new RestTemplate().getForObject("http://nacos.12301.io/nacos/v1/console/namespaces", JSONObject.class);
        if (resp != null && resp.containsKey("data")) {
            JSONArray arr = resp.getJSONArray("data");
            List<NacosNamespace> list = arr.toJavaList(NacosNamespace.class);

            System.out.println(arr.toJSONString());
        }
    }
}


package com.ch.cloud;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ch.cloud.upms.client.UpmsUserClientService;
import com.ch.cloud.upms.pojo.NacosNamespace;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = UpmsApplication.class)
public class ClientTests {

    //	@Autowired
    UpmsUserClientService upmsClientService;

    Object o;

    //	@Test
    public void contextLoads() {

//		o = upmsClientService.findPermissionsByRoleId(0L);
//        o = upmsClientService.findMenusByRoleId(0L);

        System.out.println(o);
    }

//    @Test
    public void namespaces() {

        JSONObject resp = new RestTemplate().getForObject("http://nacos.12301.io/nacos/v1/console/namespaces", JSONObject.class);
        if (resp != null && resp.containsKey("data")) {
            JSONArray arr = resp.getJSONArray("data");
            List<NacosNamespace> list = arr.toJavaList(NacosNamespace.class);
            System.out.println(arr.toJSONString());
        }
        String param = "show=all&namespaceId="+"8aaa810e7c5f8e5f017c5f8e5f6a0000";
//        resp =  new RestTemplate().getForObject("http://nacos.12301.io/nacos/v1/console/namespaces?"+param,JSONObject.class);
//        assert resp != null;
//        System.out.println(resp.toJSONString());
    }

    @Test
    public void namespaceAdd() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("customNamespaceId", "test");
        param.add("customNamespaceId", "test");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);

//        Boolean sync = new RestTemplate().postForObject("http://nacos.12301.io/nacos/v1/console/namespaces", httpEntity, Boolean.class);
//        System.out.println(sync);
    }
}


package com.ch.cloud;

import com.alibaba.fastjson.JSON;
import com.ch.cloud.upms.model.OPRecord;
import com.ch.cloud.upms.mq.RequestLogsConsumer;
import com.ch.utils.CharUtils;
import com.ch.utils.CommonUtils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

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

        System.out.println(Arrays.toString("/abc/a/1".split("/")));

        System.out.println(CharUtils.string2Unicode("\""));
    }

    @Test
    public void testMessageRecord() {
        String msg = "{\"request\":{\"url\":\"http://api.12301.io/upms/department/1/10\",\"method\":\"GET\",\"headers\":{\"Host\":\"api.12301.io\",\"X-Request-ID\":\"a863a41f2486ae40a42aef254100a11c\",\"X-Real-IP\":\"192.168.0.171\",\"X-Forwarded-For\":\"192.168.0.171\",\"X-Forwarded-Host\":\"api.12301.io\",\"X-Forwarded-Port\":\"80\",\"X-Forwarded-Proto\":\"http\",\"X-Scheme\":\"http\",\"X-Original-Forwarded-For\":\"127.0.0.1\",\"cookie\":\"ZH_WIKI=node013zbkdfoavbxz1vtopvbp4wk470.node0\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"accept-encoding\":\"gzip, deflate, br\",\"referer\":\"http://localhost:9527/\",\"sec-fetch-dest\":\"empty\",\"sec-fetch-mode\":\"cors\",\"sec-fetch-site\":\"same-origin\",\"sec-ch-ua-platform\":\"\"Windows\"\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36\",\"sec-ch-ua-mobile\":\"?0\",\"x-token\":\"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMjQ2NTk4NiwidXNlcklkIjpudWxsLCJyb2xlSWQiOjEsImNyZWF0ZWQiOjE2MzIzNzk1ODY0NzF9.PIdNmAdN_jByuapOMPrKTaMvdsVEK7bGVh3qnUBTgyFNCmSwhcoLyUTQTSYXHi86qDwtJnswR6MP0xQyuLwCqw\",\"accept\":\"application/json, text/plain, */*\",\"sec-ch-ua\":\"\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"\",\"X-AUTH-USER\":\"admin\"}}}\n" +
                "[REQUEST_PROCESS_SEPARATOR]\n" +
                "{\"proxy\":{\"url\":\"http://10.42.1.99:8080/department/1/10\",\"method\":\"GET\",\"headers\":{\"Host\":\"api.12301.io\",\"X-Request-ID\":\"a863a41f2486ae40a42aef254100a11c\",\"X-Real-IP\":\"192.168.0.171\",\"X-Forwarded-For\":\"192.168.0.171\",\"X-Forwarded-Host\":\"api.12301.io\",\"X-Forwarded-Port\":\"80\",\"X-Forwarded-Proto\":\"http\",\"X-Scheme\":\"http\",\"X-Original-Forwarded-For\":\"127.0.0.1\",\"cookie\":\"ZH_WIKI=node013zbkdfoavbxz1vtopvbp4wk470.node0\",\"accept-language\":\"zh-CN,zh;q=0.9\",\"accept-encoding\":\"gzip, deflate, br\",\"referer\":\"http://localhost:9527/\",\"sec-fetch-dest\":\"empty\",\"sec-fetch-mode\":\"cors\",\"sec-fetch-site\":\"same-origin\",\"sec-ch-ua-platform\":\"\"Windows\"\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36\",\"sec-ch-ua-mobile\":\"?0\",\"x-token\":\"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMjQ2NTk4NiwidXNlcklkIjpudWxsLCJyb2xlSWQiOjEsImNyZWF0ZWQiOjE2MzIzNzk1ODY0NzF9.PIdNmAdN_jByuapOMPrKTaMvdsVEK7bGVh3qnUBTgyFNCmSwhcoLyUTQTSYXHi86qDwtJnswR6MP0xQyuLwCqw\",\"accept\":\"application/json, text/plain, */*\",\"sec-ch-ua\":\"\"Google Chrome\";v=\"93\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"93\"\",\"X-AUTH-USER\":\"admin\"}}}\n" +
                "[REQUEST_PROCESS_SEPARATOR]\n" +
                "{\"response\":{\"status\":\"200\",\"headers\":{\"transfer-encoding\":\"chunked\",\"Date\":\"Fri, 24 Sep 2021 01:59:41 GMT\",\"Vary\":[\"Origin\",\"Access-Control-Request-Method\",\"Access-Control-Request-Headers\"],\"Content-Type\":\"application/json\"},\"data\":{\"status\":1,\"rows\":[{\"id\":1,\"pid\":\"0\",\"parentId\":\"0\",\"parentName\":null,\"name\":\"朝华科技\",\"sort\":1,\"leader\":\"朝华\",\"phone\":\"17600807713\",\"email\":null,\"status\":\"1\",\"createBy\":\"sys\",\"createAt\":1593786245000,\"updateBy\":\"admin\",\"updateAt\":1599778246000,\"children\":[{\"id\":3,\"pid\":\"1\",\"parentId\":\"1\",\"parentName\":\"朝华科技\",\"name\":\"懂事会\",\"sort\":1,\"leader\":\"ch\",\"phone\":null,\"email\":null,\"status\":\"1\",\"createBy\":\"admin\",\"createAt\":1593821501000,\"updateBy\":\"admin\",\"updateAt\":1599778273000,\"children\":null},{\"id\":2,\"pid\":\"1\",\"parentId\":\"1\",\"parentName\":\"朝华科技\",\"name\":\"深圳总部\",\"sort\":2,\"leader\":\"sz\",\"phone\":null,\"email\":null,\"status\":\"1\",\"createBy\":\"admin\",\"createAt\":1593821460000,\"updateBy\":\"admin\",\"updateAt\":1599778277000,\"children\":[{\"id\":5,\"pid\":\"2\",\"parentId\":\"1,2\",\"parentName\":\"朝华科技,深圳总部\",\"name\":\"架构部门\",\"sort\":2,\"leader\":\"a\",\"phone\":null,\"email\":null,\"status\":\"1\",\"createBy\":\"admin\",\"createAt\":1593832347000,\"updateBy\":\"admin\",\"updateAt\":1599778285000,\"children\":null},{\"id\":6,\"pid\":\"2\",\"parentId\":\"1,2\",\"parentName\":\"朝华科技,深圳总部\",\"name\":\"大数据部门\",\"sort\":3,\"leader\":\"b\",\"phone\":null,\"email\":null,\"status\":\"1\",\"createBy\":\"admin\",\"createAt\":1593832390000,\"updateBy\":\"admin\",\"updateAt\":1599778288000,\"children\":null},{\"id\":4,\"pid\":\"2\",\"parentId\":\"1,2\",\"parentName\":\"朝华科技,深圳总部\",\"name\":\"业务研发部\",\"sort\":4,\"leader\":\"yf\",\"phone\":null,\"email\":null,\"status\":\"1\",\"createBy\":\"admin\",\"createAt\":1593821534000,\"updateBy\":\"admin\",\"updateAt\":1599778304000,\"children\":[{\"id\":7,\"pid\":\"4\",\"parentId\":\"1,2,4\",\"parentName\":\"朝华科技,深圳总部,业务研发部\",\"name\":\"产品组\",\"sort\":1,\"leader\":\"av\",\"phone\":null,\"email\":null,\"status\":\"1\",\"createBy\":\"admin\",\"createAt\":1593832580000,\"updateBy\":\"\",\"updateAt\":1599778307000,\"children\":null},{\"id\":9,\"pid\":\"4\",\"parentId\":\"1,2,4\",\"parentName\":\"朝华科技,深圳总部,业务研发部\",\"name\":\"测试组\",\"sort\":3,\"leader\":\"a3\",\"phone\":null,\"email\":null,\"status\":\"1\",\"createBy\":\"admin\",\"createAt\":1593832631000,\"updateBy\":\"\",\"updateAt\":1599778309000,\"children\":null},{\"id\":8,\"pid\":\"4\",\"parentId\":\"1,2,4\",\"parentName\":\"朝华科技,深圳总部,业务研发部\",\"name\":\"研发组\",\"sort\":5,\"leader\":\"b1\",\"phone\":null,\"email\":null,\"status\":\"1\",\"createBy\":\"admin\",\"createAt\":1593832605000,\"updateBy\":\"admin\",\"updateAt\":1599778082000,\"children\":null}]}]}]}],\"code\":null,\"message\":null,\"extra\":null,\"timestamp\":1632448781754,\"total\":1,\"success\":true}}}\n" +
                "[REQUEST_PROCESS_SEPARATOR]\n" +
                "{\"record\":{\"url\":\"/upms/department/1/10\",\"method\":\"GET\",\"username\":\"admin\",\"startTimestamp\":\"1632448781719\",\"endTimestamp\":\"1632448781763\"}}";
//        RequestLogsConsumer consumer = new RequestLogsConsumer();
//
//        OPRecord record = consumer.parseRecord(msg);
//        System.out.println(JSON.toJSON(record));
    }
}

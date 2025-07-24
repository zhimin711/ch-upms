# 用户权限管理微服务(ch-upms)

#### 介绍
UPMS是User Permissions Management Service，用户权限管理微服务.  
* 使用Spring Boot + Mybatis Plus框架
* 使用Alibaba Nacos 为注册与配置中心
* 使用RocketMQ为消息总线，存储登录与操作日志
* 使用前后端分离方式（前端使用Vue），该服务为前端提供Restful Api
* 使用Mybatis Plus 实现逻辑删除
#### 软件架构
请接Wiki文档 [传送门](https://gitee.com/ch-cloud/wiki)

[docker](https://docs.docker.com/engine/security/certificates)

#### 安装教程
maven need install local jar
```shell
mvn install:install-file -Dfile=xxx/libs/third/ueditor-1.1.2.jar -DgroupId=com.baidu -DartifactId=ueditor -Dversion=1.1.2 -Dpackaging=jar
```

```shell
mvn clean install -Drevision=1.0.1-SNAPSHOT
```
### 打包client模块
```shell
mvn -U -pl client -am clean install -Dmaven.test.skip -Drevision=1.1.1-SNAPSHOT

mvn -U -pl client -am clean deploy -Dmaven.test.skip -Drevision=1.1.1-SNAPSHOT

```



1. 初化数据库  
使用other目录db初始化数据库
2. 修改配置文件（基于Wiki基础服务）  
（1） resources/config/application-local.yml  
        修改database的Url、用户名、密码
        修改RocketMq地址
```yaml
jasypt:
  encryptor:
    password: abc123
    algorithm: PBEWithMD5AndDES
spring:
  application:
    name: ch-upms
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://106.12.140.185:3306/dev_ch_upms
    username: admin
    password: ENC(hfO3JFDCY2HB6x+j1obZOg==)
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      minimum-idle: 5
      maximum-pool-size: 15
      auto-commit: true
      idle-timeout: 30000
      pool-name: DatebookHikariCP
      connection-timeout: 30000
      connection-test-query: SELECT 1
  thymeleaf:
    mode: HTML
    cache: false
  servlet:
    multipart:
      max-file-size: 100MB
logging:
  config: classpath:config/logback.xml
  path: logs/${spring.application.name}
  level:
    com.ch: debug
    com.alibaba.nacos.client: warn

path:
  temp: share/tmp
  root: D:\\git\\ch-cloud-new\\ch-admin2
  upload: ${path.root}\\upload
  download: share/download


rocketmq:
  name-server: 192.168.199.194:9876 # 自己的RocketMQ服务地址
  producer:
    send-message-timeout: 300000
    group: ch-upms
#====================================mybatis-plus config===============================================
mybatis-plus:
  mapper-locations: classpath*:/mapper/*.xml
  typeAliasesPackage: com.ch.cloud.upms.model
  configuration:
    map-underscore-to-camel-case: true
  global-config:
    db-config:
      logic-delete-field: deleted
      id-type: auto

```
（2） resources/bootstrap.yml  
修改namespace与server-addr
```yaml
nacos:
  config:
    namespace: local
    server-addr: 192.168.199.194:8848
spring:
  application:
    name: ch-upms
  cloud:
    nacos:
      discovery:
        server-addr: ${nacos.config.server-addr}
        namespace: ${nacos.config.namespace:}
      config:
        server-addr: ${nacos.config.server-addr}
        namespace: ${nacos.config.namespace:}
        file-extension: yml

```
3. 上传配置文件（application-local.yml,注:文件名要修改为"应用名称".yml（spring.application.name））到Nacos

4. 启动服务
~~~
#gradle工具命令启动：
gradle bootJar
#docker部署参考other目录deploy.md
~~~

#### 使用说明
基于前端[ch-admin3](https://gitee.com/ch-cloud/ch-admin3)实现系统管理与日志管理
1. 用户管理
<table>
    <tr>
        <td>用户管理</td>
        <td>新增用户</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/user.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/user_add.png"/></td>
    </tr>
    <tr>
        <td>修改用户</td>
        <td>删除用户</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/user_edit.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/user_del.png"/></td>
    </tr>
    <tr>
        <td>分配角色</td>
        <td>初始化用户密码</td>
    </tr>
	<tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/user_role.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/user_init_pwd.png"/></td>
    </tr>	
</table>
2. 权限管理
<table>
    <tr>
        <td>权限管理</td>
        <td>新增权限</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/permission.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/permission_add.png"/></td>
    </tr>
    <tr>
        <td>修改或复制权限</td>
        <td>删除权限</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/permission_edit.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/permission_del.png"/></td>
    </tr>
</table>
3. 角色管理
<table>
    <tr>
        <td>角色管理</td>
        <td>新增角色</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/role.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/role_add.png"/></td>
    </tr>
    <tr>
        <td>修改角色</td>
        <td>删除角色</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/role_edit.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/role_del.png"/></td>
    </tr>
    <tr>
        <td>分配权限</td>
        <td></td>
    </tr>
	<tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/role_permission.png"/></td>
        <td></td>
    </tr>	
</table>
4. 组织管理
<table>
    <tr>
        <td>组织管理</td>
        <td>新增组织</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/dept.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/department_add.png"/></td>
    </tr>
    <tr>
        <td>修改组织</td>
        <td>删除组织</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/department_edit.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/department_del.png"/></td>
    </tr>
    <tr>
        <td>分配职位</td>
        <td></td>
    </tr>
	<tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/department_post.png"/></td>
        <td></td>
    </tr>	
</table>
5. 职位管理
<table>
    <tr>
        <td>职位管理</td>
        <td>新增职位</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/post.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/post_add.png"/></td>
    </tr>
    <tr>
        <td>修改职位</td>
        <td>删除职位</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/post_edit.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/post_del.png"/></td>
    </tr>
</table>
6. 数据字典管理
<table>
    <tr>
        <td>数据字典</td>
        <td>新增字典</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/dict.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/dict_add.png"/></td>
    </tr>
    <tr>
        <td>修改字典</td>
        <td>删除字典</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/dict_edit.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/upms/dict_del.png"/></td>
    </tr>
</table>
7. 登录日志 & 操作日志
<table>
    <tr>
        <td>登录日志</td>
        <td>操作日志</td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/login_logs.png"/></td>
        <td><img src="https://gitee.com/ch-cloud/wiki/raw/master/images/operate_logs.png"/></td>
    </tr>
</table>


#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


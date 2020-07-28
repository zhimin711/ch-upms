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


#### 安装教程

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
基于[ch-admin3](https://gitee.com/ch-cloud/ch-admin3)实现系统管理与日志管理
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
        <td>登录</td>
        <td>首页</td>
    </tr>
    <tr>
        <td><img src="./images/login.png"/></td>
        <td><img src="./images/home.png"/></td>
    </tr>
    <tr>
        <td>用户管理</td>
        <td>角色管理</td>
    </tr>
    <tr>
        <td><img src="./images/user.png"/></td>
        <td><img src="./images/role.png"/></td>
    </tr>
    <tr>
        <td>权限管理</td>
        <td>组织管理</td>
    </tr>
    <tr>
        <td><img src="./images/permission.png"/></td>
        <td><img src="./images/dept.png"/></td>
    </tr>
    <tr>
        <td>职位管理</td>
        <td>数据字典</td>
    </tr>
	<tr>
        <td><img src="./images/post.png"/></td>
        <td><img src="./images/dict.png"/></td>
    </tr>	
    <tr>
        <td>登录日志</td>
        <td>操作日志</td>
    </tr> 
    <tr>
        <td><img src="./images/login_logs.png"/></td>
        <td><img src="./images/operate_logs.png"/></td>
    </tr>
</table>
3. 角色管理
<table>
    <tr>
        <td>登录</td>
        <td>首页</td>
    </tr>
    <tr>
        <td><img src="./images/login.png"/></td>
        <td><img src="./images/home.png"/></td>
    </tr>
    <tr>
        <td>用户管理</td>
        <td>角色管理</td>
    </tr>
    <tr>
        <td><img src="./images/user.png"/></td>
        <td><img src="./images/role.png"/></td>
    </tr>
    <tr>
        <td>权限管理</td>
        <td>组织管理</td>
    </tr>
    <tr>
        <td><img src="./images/permission.png"/></td>
        <td><img src="./images/dept.png"/></td>
    </tr>
    <tr>
        <td>职位管理</td>
        <td>数据字典</td>
    </tr>
	<tr>
        <td><img src="./images/post.png"/></td>
        <td><img src="./images/dict.png"/></td>
    </tr>	
    <tr>
        <td>登录日志</td>
        <td>操作日志</td>
    </tr> 
    <tr>
        <td><img src="./images/login_logs.png"/></td>
        <td><img src="./images/operate_logs.png"/></td>
    </tr>
</table>
4. 组织管理
<table>
    <tr>
        <td>登录</td>
        <td>首页</td>
    </tr>
    <tr>
        <td><img src="./images/login.png"/></td>
        <td><img src="./images/home.png"/></td>
    </tr>
    <tr>
        <td>用户管理</td>
        <td>角色管理</td>
    </tr>
    <tr>
        <td><img src="./images/user.png"/></td>
        <td><img src="./images/role.png"/></td>
    </tr>
    <tr>
        <td>权限管理</td>
        <td>组织管理</td>
    </tr>
    <tr>
        <td><img src="./images/permission.png"/></td>
        <td><img src="./images/dept.png"/></td>
    </tr>
    <tr>
        <td>职位管理</td>
        <td>数据字典</td>
    </tr>
	<tr>
        <td><img src="./images/post.png"/></td>
        <td><img src="./images/dict.png"/></td>
    </tr>	
    <tr>
        <td>登录日志</td>
        <td>操作日志</td>
    </tr> 
    <tr>
        <td><img src="./images/login_logs.png"/></td>
        <td><img src="./images/operate_logs.png"/></td>
    </tr>
</table>
5. 职位管理
<table>
    <tr>
        <td>登录</td>
        <td>首页</td>
    </tr>
    <tr>
        <td><img src="./images/login.png"/></td>
        <td><img src="./images/home.png"/></td>
    </tr>
    <tr>
        <td>用户管理</td>
        <td>角色管理</td>
    </tr>
    <tr>
        <td><img src="./images/user.png"/></td>
        <td><img src="./images/role.png"/></td>
    </tr>
    <tr>
        <td>权限管理</td>
        <td>组织管理</td>
    </tr>
    <tr>
        <td><img src="./images/permission.png"/></td>
        <td><img src="./images/dept.png"/></td>
    </tr>
    <tr>
        <td>职位管理</td>
        <td>数据字典</td>
    </tr>
	<tr>
        <td><img src="./images/post.png"/></td>
        <td><img src="./images/dict.png"/></td>
    </tr>	
    <tr>
        <td>登录日志</td>
        <td>操作日志</td>
    </tr> 
    <tr>
        <td><img src="./images/login_logs.png"/></td>
        <td><img src="./images/operate_logs.png"/></td>
    </tr>
</table>
6. 数据字典管理
<table>
    <tr>
        <td>登录</td>
        <td>首页</td>
    </tr>
    <tr>
        <td><img src="./images/login.png"/></td>
        <td><img src="./images/home.png"/></td>
    </tr>
    <tr>
        <td>用户管理</td>
        <td>角色管理</td>
    </tr>
    <tr>
        <td><img src="./images/user.png"/></td>
        <td><img src="./images/role.png"/></td>
    </tr>
    <tr>
        <td>权限管理</td>
        <td>组织管理</td>
    </tr>
    <tr>
        <td><img src="./images/permission.png"/></td>
        <td><img src="./images/dept.png"/></td>
    </tr>
    <tr>
        <td>职位管理</td>
        <td>数据字典</td>
    </tr>
	<tr>
        <td><img src="./images/post.png"/></td>
        <td><img src="./images/dict.png"/></td>
    </tr>	
    <tr>
        <td>登录日志</td>
        <td>操作日志</td>
    </tr> 
    <tr>
        <td><img src="./images/login_logs.png"/></td>
        <td><img src="./images/operate_logs.png"/></td>
    </tr>
</table>
7. 登录日志
<table>
    <tr>
        <td>登录</td>
        <td>首页</td>
    </tr>
    <tr>
        <td><img src="./images/login.png"/></td>
        <td><img src="./images/home.png"/></td>
    </tr>
    <tr>
        <td>用户管理</td>
        <td>角色管理</td>
    </tr>
    <tr>
        <td><img src="./images/user.png"/></td>
        <td><img src="./images/role.png"/></td>
    </tr>
    <tr>
        <td>权限管理</td>
        <td>组织管理</td>
    </tr>
    <tr>
        <td><img src="./images/permission.png"/></td>
        <td><img src="./images/dept.png"/></td>
    </tr>
    <tr>
        <td>职位管理</td>
        <td>数据字典</td>
    </tr>
	<tr>
        <td><img src="./images/post.png"/></td>
        <td><img src="./images/dict.png"/></td>
    </tr>	
    <tr>
        <td>登录日志</td>
        <td>操作日志</td>
    </tr> 
    <tr>
        <td><img src="./images/login_logs.png"/></td>
        <td><img src="./images/operate_logs.png"/></td>
    </tr>
</table>
8. 操作日志
<table>
    <tr>
        <td>登录</td>
        <td>首页</td>
    </tr>
    <tr>
        <td><img src="./images/login.png"/></td>
        <td><img src="./images/home.png"/></td>
    </tr>
    <tr>
        <td>用户管理</td>
        <td>角色管理</td>
    </tr>
    <tr>
        <td><img src="./images/user.png"/></td>
        <td><img src="./images/role.png"/></td>
    </tr>
    <tr>
        <td>权限管理</td>
        <td>组织管理</td>
    </tr>
    <tr>
        <td><img src="./images/permission.png"/></td>
        <td><img src="./images/dept.png"/></td>
    </tr>
    <tr>
        <td>职位管理</td>
        <td>数据字典</td>
    </tr>
	<tr>
        <td><img src="./images/post.png"/></td>
        <td><img src="./images/dict.png"/></td>
    </tr>	
    <tr>
        <td>登录日志</td>
        <td>操作日志</td>
    </tr> 
    <tr>
        <td><img src="./images/login_logs.png"/></td>
        <td><img src="./images/operate_logs.png"/></td>
    </tr>
</table>


#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


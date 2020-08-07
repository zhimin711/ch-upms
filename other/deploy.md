
#### 上传文件
```
scp -r src/main/docker/Dockerfile zhimin@192.168.1.100:/home/zhimin/docker/ch-upms
scp -r build/libs/ch-upms-1.0.0-SNAPSHOT.jar zhimin@192.168.199.194:/home/zhimin/docker/ch-upms
```
####   打包docker镜像  
基于src/main/docker/Dockerfile打包
```
docker build -t ch-upms:v1 /home/zhimin/docker/ch-upms
```

#### 启动
~~~
#使用宿主机端口
docker run --name ch-upms \
-p 7002:7002 \
-v /home/zhimin/share:/mnt/share \
-m 550M --memory-swap -1 \
-d ch-upms:v1;
#无网络
docker run --name ch-upms \
--net=none \
-v /home/zhimin/share:/mnt/share  \
-v /home/zhimin/docker/hosts:/etc/hosts  \
-m 512M --memory-swap -1 \
-d ch-upms:v1;
~~~
####  重启 停止 删除
```
docker start ch-upms;
docker restart ch-upms; 
docker stop ch-upms;
docker rm ch-upms;
docker rmi ch-upms:v1;
```
#### 网络
```无网络分配IP
pipework br0 ch-upms 192.168.1.30/24@192.168.1.1;
```
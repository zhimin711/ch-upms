
#### 上传文件
```
scp -r src/main/docker/Dockerfile zhimin@192.168.1.100:/home/zhimin/docker/ch-admin
scp -r build/libs/ch-admin-1.0.0-SNAPSHOT.jar zhimin@192.168.1.100:/home/zhimin/docker/ch-admin
```
####   打包
```
sudo docker build -t ch-admin:v1 /home/zhimin/docker/ch-admin
```

#### 启动
```
sudo docker run --name ch-admin \
--net=none \
-v /home/zhimin/share:/mnt/share  \
-m 512M --memory-swap -1 \
-d ch-admin:v1 ;
```
####  重启 停止 删除
```
sudo docker restart ch-admin; 
sudo docker stop ch-admin;
sudo docker rm ch-admin;
sudo docker rmi ch-admin:v1;
```
#### 网络
```
sudo pipework br0 ch-admin 192.168.1.30/24@192.168.1.1;
```
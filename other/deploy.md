
#### 上传文件
```
scp -r src/main/docker/Dockerfile zhimin@192.168.1.100:/home/zhimin/docker/ch-upms
scp -r build/libs/ch-upms-1.0.0-SNAPSHOT.jar zhimin@192.168.1.100:/home/zhimin/docker/ch-upms
```
####   打包
```
sudo docker build -t ch-upms:v1 /home/zhimin/docker/ch-upms
```

#### 启动
```
sudo docker run --name ch-upms \
--net=none \
-v /home/zhimin/share:/mnt/share  \
-m 512M --memory-swap -1 \
-d ch-upms:v1;
```
####  重启 停止 删除
```
sudo docker start ch-upms;
sudo docker restart ch-upms; 
sudo docker stop ch-upms;
sudo docker rm ch-upms;
sudo docker rmi ch-upms:v1;
```
#### 网络
```
sudo pipework br0 ch-upms 192.168.1.30/24@192.168.1.1;
```


### 打包client模块
```shell
mvn -U -pl client -am clean install -Dmaven.test.skip -Drevision=1.1.0-SNAPSHOT
```

#### 上传文件
```shell script
scp -r src/main/docker/Dockerfile zhimin@192.168.1.100:/home/zhimin/docker/ch-upms
# window 
scp -r build/libs/ch-upms-1.0.0-SNAPSHOT.jar zhimin@192.168.0.109:/home/zhimin/docker/ch-upms
```
####   打包docker镜像  
基于src/main/docker/Dockerfile打包
```shell script
docker build -t ch-upms:v1 /home/zhimin/docker/ch-upms
```

#### 启动
~~~shell script
#使用宿主机端口
docker run --name ch-upms \
-p 7002:7002 \
-v /home/zhimin/share:/mnt/share \
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
```shell script
docker start ch-upms;
docker restart ch-upms; 
docker stop ch-upms;
docker rm ch-upms;
docker rmi ch-upms:v1;
```
#### 网络
```shell script
#无网络分配IP
pipework br0 ch-upms 192.168.1.30/24@192.168.1.1;
```

### Jenkins Job
```groovy
// parameters {
//     gitParameter branchFilter: 'origin/(.*)', defaultValue: 'master', name: 'BRANCH', type: 'PT_BRANCH'
// }
def label = "jnlp-${JOB_NAME}"
def app_name = "ch-upms"
def img_name = "ch-upms:${DATETIME}"
def img_namespace = "ch"
def docker_api = "-H tcp://192.168.0.253:2375"
def hub_addr = "registry.kubeoperator.io:8085"
def k8s_hub_addr = "registry.kubeoperator.io:8082"
def k8s_url = "https://192.168.0.252:8443"

podTemplate(label: label, cloud: 'kubernetes', inheritFrom: 'jenkins-slave-maven') {
    node(label) {
        stage('Checkout Project') {
            echo "1.Clone Project "
            git credentialsId: 'CHGitee2', url: 'https://gitee.com/ch-cloud/ch-upms.git/', branch: "${params.BRANCH}"
        }
        stage('Build project') {
            container('maven') {
                // sh 'git config --global url."https://".insteadOf ssh://git@'
                echo "2.Build Project Deploy Package File"
                sh 'mvn clean package -U'
                sh "cp ${WORKSPACE}/../apache-skywalking-java-agent-8.11.0.tgz ${WORKSPACE}/web/target"
            }
        }
        stage('Build Image') {
            echo "3.Build Project Docker Image"
            // sh "cd ${WORKSPACE}/web"
            container('docker') {
                sh "docker ${docker_api} build -t ${img_name} -f ${WORKSPACE}/web/src/main/docker/Dockerfile ${WORKSPACE}/web/target"
                sh "docker ${docker_api} tag ${img_name} ${hub_addr}/${img_namespace}/${img_name}"
            }

        }
        stage('Push Image') {
            echo "4.Push Project Docker Image"
            container('docker')  {
                withCredentials([usernamePassword(credentialsId: 'Nexus', passwordVariable: 'dockerPassword', usernameVariable: 'dockerUser')]) {
                    sh "docker ${docker_api} login -u ${dockerUser} -p ${dockerPassword} ${hub_addr}"
                    sh "docker ${docker_api} push ${hub_addr}/${img_namespace}/${img_name}"
                    sh "docker ${docker_api} rmi ${hub_addr}/${img_namespace}/${img_name} ${img_name}"
                }
            }
        }
        stage('Deploy Image') {
            echo "5.Deploy Project Docker Image"
            container ('docker') {
                script{
                    out=sh(script:"ls ./kubectl",returnStatus:true)
                    println "--------------"
                    println out
                    if(out == 0){
                        println "file is exist"
                    } else if(out == 1 || out == 2){
                        println "file is not exist"
                        sh 'cp ../kubectl .'
                        sh 'chmod u+x ./kubectl'
                    } else {
                        error("command is error,please check")
                    }
                }
                withKubeConfig([credentialsId:'kubeMaster'
                                ,serverUrl: "${k8s_url}"
                                ,namespace: "ch"]) {
                    sh "./kubectl set image deployment/${app_name} *=${k8s_hub_addr}/${img_namespace}/${img_name}"
                }
            }
        }
    }
}
```

### Jenkins release Job
```groovy
// parameters {
//     gitParameter branchFilter: 'origin/(.*)', defaultValue: 'master', name: 'BRANCH', type: 'PT_BRANCH'
// }
def label = "jnlp-${JOB_NAME}"
def docker_api = "-H tcp://192.168.0.253:2375"
def img_name = "ch-upms:${DATETIME}"
// def hub_addr = "192.168.0.253:8083"
def hub_addr = "registry.cn-hangzhou.aliyuncs.com"
def hub_namespace = "ch-cloud"

podTemplate(label: label, cloud: 'kubernetes', inheritFrom: 'jenkins-slave-maven') {
    node(label) {
            stage('Checkout Project') {
                echo "1.Clone Project "
                git credentialsId: 'CHGitee2', url: 'https://gitee.com/ch-cloud/ch-upms.git/', branch: "${params.BRANCH}"
            }
            stage('Build project') {
                container('maven') {
                    //sonarqube maven
                    // sh 'mvn clean verify sonar:sonar'
                    // sh 'git config --global url."https://".insteadOf ssh://git@'
                    echo "2.Build Project Deploy Package File"
                    sh 'mvn clean package -U'
                    // sh 'mvn verify sonar:sonar'
                    sh "cp ${WORKSPACE}/../apache-skywalking-java-agent-8.11.0.tgz ${WORKSPACE}/web/target"
                }
            }
            stage('Build Image') {
                echo "3.Build Project Docker Image"
                // sh "cd ${WORKSPACE}/web"
                container('docker') {
                    sh "docker ${docker_api} build -t ${img_name} -f ${WORKSPACE}/web/src/main/docker/Dockerfile ${WORKSPACE}/web/target"
                    sh "docker ${docker_api} tag ${img_name} ${hub_addr}/${hub_namespace}/${img_name}"
                }

            }
            stage('Push Image') {
                echo "4.Push Project Docker Image"
                container('docker')  {
                    withCredentials([usernamePassword(credentialsId: 'aliHub', passwordVariable: 'dockerPassword', usernameVariable: 'dockerUser')]) {
                        sh "docker ${docker_api} login -u ${dockerUser} -p ${dockerPassword} ${hub_addr}"
                        sh "docker ${docker_api} push ${hub_addr}/${hub_namespace}/${img_name}"
                        sh "docker ${docker_api} rmi ${hub_addr}/${hub_namespace}/${img_name} ${img_name}"
                    }
                }
            }
    }
}
```

### Jenkins Job
```groovy

```

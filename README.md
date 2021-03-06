# 概述

基于单一主干分支策略的CI验证项目，该项目仅有一个控制器，用于接受两个输入参数进行加法运算，然后将结果保存到MySQL数据库中。

重点在于需要使用Docker，并结合Jenkins进行相关构建和测试，目标是每次代码提交将自动触发构建任务。

# 步骤以及相关问题

## 安装Gradle插件

通过Jenkins的管理界面进行安装，或者运行Jenkins提供的`install-plugins.sh`脚本：

```
install-plugins.sh gradle
```

## 容器镜像文件

接下来的一个问题是在哪里执行Gradle，因为测试需要执行的是源代码级别测试（如同在本地环境测试一样），需要同时用到Gradle以及MySQL两个程序，由于一般镜像文件只提供一个应用，意味着如果使用两个不同的容器，就需要将同一份代码分别放入这两个容器中执行，目前使用流水线实现起来有一定难度，要么就需要自行构建一个同时包含两个程序的镜像文件，相比于插件，其优缺点如下：

* 优点：无需在每一个Jenkins执行节点上安装所需的软件，减少运维成本，可以直接在流水线中引用即可；
* 缺点：
 - 如果运行环境依赖的服务越多，则镜像的构建就越复杂，镜像文件也会越大；
 - 不同服务对缓存数据的要求不同，有些服务比如Gradle需要缓存已经下载的jar文件，而MySQL则不需要已经插入的数据，二者放在同一个镜像中对此存在着矛盾，不能简单地在容器推出后删除数据卷。

> 使用私有仓库预先下载镜像文件：`docker pull klr.io:6789/gradle:5.4.1-jdk8-alpine`，同时需要开放必要的端口以便Jenkins能够访问（如私有仓库的相应下载端口）。

将Gradle官方镜像中的内容拷贝到MySQL基础镜像之中，构建后推送到私有仓库便能在流水线中使用，具体参见[Dockerfile](docker/Dockerfile-gradle-mysql)。注意`CMD`命令，不能覆盖原有MySQL的设置，因此需要将Gradle镜像中的`CMD`命令删除。

为了能够缓存Gradle数据，还需要增加参数`-v gradle-cache:/home/gradle/.gradle`。

## 容器自动退出问题

在`docker`类型的`agent`内执行完操作之后，该容器会自动退出和删除，因此无法应用到下一个stage，对于MySQL容器的情形，就不能简单地将其作为一个独立的容器运行而后在后续stage中连接使用，只能通过`sh`手工启动和关闭，以便后续stage使用。

## 容器网络设置

默认情况下容器以`bridge`模式启动，其地址一般是`172.17.x.x`，通过虚拟网卡`docker0`访问，也可以通过`host`模式启动，那么容器地址就是主机地址。对于前者，显然不能继续以`localhost`访问，必须指定网络地址，但也必须考虑到多个流水线运行时避免地址冲突的问题；后者虽然可以直接使用`localhost`地址，但也需要注意多个流水线同时运行的问题，可以通过指定不同的端口分别访问。

另外后续stage中的容器如果需要访问MySQL，则必须处于同一个网络，因此需要加上`--network`选项启动。

## Jenkinsfile

在项目根目录下创建`Jenkinsfile`文件，注意需要更新子模块。

```
git submodule update --init
```

## 工具

可以在Jenkins的全局工具配置中配置构建所需要的各类工具，如Git和Gradle等，而无需混合不同的镜像，上述Gradle容器也可以用此功能代替。但仍需注意的是即便配置了全局工具，在容器中也将无法使用。

## 工作空间

不同stage的工作空间并不相同，以`workspace@<num>`的形式命名，因此即便在第一步更新了子模块，第二步中的工作空间仍然无法看到。

可以通过设置`reuseNode`修改：

```
docker {
    reuseNode true
}
```

## 字符串引号以及验证问题

为了能够使用Jenkins API验证`Jenkinsfile`，建议统一使用单引号或者双引号表示字符串，不要混合使用，因为调用API时需要读取文件文件内容：

```
curl -X POST -F "jenkinsfile=$(cat /opt/Jenkinsfile)" -u admin:admin http://192.168.0.5:8080/pipeline-model-converter/validate
```

## 创建流水线

在Blue Ocean中直接创建，选择Git或者GitHub输入相应仓库地址即可（需要包含`Jenkinsfile`文件）。

![create-pipeline-from-git](img/create-pipeline-from-git.png)

## 触发构建

如果都能通过外网访问，则推荐使用Webhook；如果是Git hooks，则需要考虑Windows机器下的脚本执行问题。
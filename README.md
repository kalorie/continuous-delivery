# 概述

基于单一主干分支策略的CI验证项目，该项目仅有一个控制器，用于接受两个输入参数进行加法运算，然后将结果保存到MySQL数据库中。

重点在于需要使用Docker，并结合Jenkins进行相关构建和测试，目标是每次代码提交将自动触发构建任务。

# 步骤

## 安装Gradle插件

通过Jenkins的管理界面进行安装，或者运行Jenkins提供的`install-plugins.sh`脚本：

```
install-plugins.sh gradle
```

## Jenkinsfile

在项目根目录下创建`Jenkinsfile`文件，内容如下所示：

```groovy
pipeline {
    agent {
        docker {
            image gradle:5.4.1-jdk8-alpine
        }
    }

    stages {
        stage("Clone project") {
            sh 'gradle --version'
        }
    }
}
```
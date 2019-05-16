#!/usr/bin/env groovy

pipeline {
    environment {
        ROOT_PASSWORD = "root"
        DATABASE = "test"
    }

    agent {
        docker {
            image 'gradle-mysql:latest'
            args "-u root -v gradle-cache:/home/gradle/.gradle -e MYSQL_ROOT_PASSWORD=${env.ROOT_PASSWORD} -e MYSQL_DATABASE=${env.DATABASE}"
        }
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
        timestamps()
    }

    stages {
        stage("Build") {
            steps {
                sh 'gradle clean test -Dspring.profiles.active=local'
            }
        }
    }
}

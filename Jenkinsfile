#!/usr/bin/env groovy

pipeline {
    agent none

    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
        timestamps()
    }

    stages {
        stage("Update submodules") {
            agent any

            steps {
                sh 'git submodule update --init'
            }
        }

        stage("Build") {
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
            steps {
                sh 'gradle clean test -Dinternet -Dspring.profiles.active=local --info'
            }
            post {
                always {
                    junit "build/test-results/*.xml"
                }
            }
        }
    }
}

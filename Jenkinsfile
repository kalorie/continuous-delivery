#!/usr/bin/env groovy

pipeline {
    agent any

    environment {
        MYSQL_NETWORK = "mysqlnet"
        DOCKER_REGISTRY = "https://klr.io:6789/"
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
        timestamps()
    }

    stages {
        stage("Update submodules") {
            steps {
                sh 'git submodule update --init'
            }
        }

        stage("Create MySQL network") {
            steps {
                sh "docker network create --subnet=172.16.0.0/16 $MYSQL_NETWORK"
            }
        }

        stage("Start MySQL") {
            environment {
                ROOT_PASSWORD = "root"
                DATABASE = "test"
                IP = "172.16.0.10"
            }
            agent {
                docker {
                    registryUrl "$DOCKER_REGISTRY"
                    image "mysql:5.7"
                    args "-u root --rm --network $MYSQL_NETWORK --ip $IP -e MYSQL_ROOT_PASSWORD=$ROOT_PASSWORD -e MYSQL_DATABASE=$DATABASE"
                }
            }
            steps {
                echo "Started MySQL..."
            }
        }

        stage("Build") {
            agent {
                docker {
                    registryUrl "$DOCKER_REGISTRY"
                    image 'gradle:5.4.1-jdk8-alpine'
                    args "-v gradle-cache:/home/gradle/.gradle"
                }
            }
            steps {
                sh 'gradle clean test -Dinternet -Dspring.profiles.active=integration-test --info'
            }
            post {
                always {
                    junit "build/test-results/**/*.xml"
                }
            }
        }
    }

    post {
        always {
            sh "docker network rm $MYSQL_NETWORK"
        }
    }
}

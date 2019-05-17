#!/usr/bin/env groovy

pipeline {
    agent any

    parameters {
        string(name: "MYSQL_NETWORK", defaultValue: "mysqlnet")
        string(name: "DOCKER_REGISTRY", defaultValue: "https://klr.io:6789/")
        string(name: "ROOT_PASSWORD", defaultValue: "root")
        string(name: "DATABASE", defaultValue: "test")
        string(name: "IP", defaultValue: "172.16.0.10")
        string(name: "SUBNET", defaultValue: "172.16.0.0/16")
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
                sh "docker network create --subnet=${params.SUBNET} ${params.MYSQL_NETWORK}"
            }
        }

        stage("Start MySQL") {
            agent {
                docker {
                    registryUrl "${params.DOCKER_REGISTRY}"
                    image "mysql:5.7"
                    args "-u root --rm --network ${params.MYSQL_NETWORK} --ip ${params.IP} -e MYSQL_ROOT_PASSWORD=${params.ROOT_PASSWORD} -e MYSQL_DATABASE=${params.DATABASE}"
                }
            }
            steps {
                echo "Started MySQL..."
            }
        }

        stage("Build") {
            agent {
                docker {
                    registryUrl "${params.DOCKER_REGISTRY}"
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
            sh "docker network rm ${params.MYSQL_NETWORK}"
        }
    }
}

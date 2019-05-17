#!/usr/bin/env groovy

pipeline {
    agent none

    environment {
        MYSQL_NETWORK = "mysqlnet"
    }

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

        stage("Create MySQL network") {
            agent any

            steps {
                sh "docker network create ${env.MYSQL_NETWORK}"
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
                    image "klr.io:6789/mysql:5.7"
                    args "-u root --rm --network ${env.MYSQL_NETWORK} --ip ${env.IP} -e MYSQL_ROOT_PASSWORD=${env.ROOT_PASSWORD} -e MYSQL_DATABASE=${env.DATABASE}"
                }
            }
            steps {
                echo "Started MySQL..."
            }
        }

        stage("Build") {
            agent {
                docker {
                    image 'klr.io:6789/gradle:5.4.1-jdk8-alpine'
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
            sh "docker network rm ${env.MYSQL_NETWORK}"
        }
    }
}

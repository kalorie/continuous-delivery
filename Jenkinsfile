#!/usr/bin/env groovy

pipeline {
    agent {
        docker {
            image 'gradle-mysql:latest'
            args "-u root -v $HOME/.gradle:/home/gradle/.gradle"
        }
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
        timestamps()
    }

    stages {
        stage("Smoke") {
            steps {
                sh 'gradle --version'
                sh "java -version"
                sh 'ls -lh'
            }
        }
    }
}

#!/usr/bin/env groovy

pipeline {
    agent {
        docker {
            image 'klr.io:6789/gradle:5.4.1-jdk8-alpine'
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
                sh 'ls .'
            }
        }
    }
}

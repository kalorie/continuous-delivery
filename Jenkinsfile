#!/usr/bin/env groovy

pipeline {
    agent {
        docker {
            image 'gradle-mysql:latest'
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

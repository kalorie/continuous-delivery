#!/usr/bin/env groovy

pipeline {
    agent {
        docker {
            image 'gradle:5.4.1-jdk8-alpine'
        }
    }

    stages {
	stage("Smoke") {
            steps {
                sh 'gradle --version'
            }
        }
    }
}

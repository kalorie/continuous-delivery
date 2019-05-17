#!/usr/bin/env groovy

pipeline {
    agent any

    triggers {
        upstream(upstreamProjects: 'continuous-delivery', threshold: hudson.model.Result.SUCCESS)
    }

    stages {
        stage('React') {
            steps {
                sh 'Reacted based on the upstreams at $(date)'
            }
        }
    }
}

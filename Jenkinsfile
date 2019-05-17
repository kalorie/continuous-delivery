#!/usr/bin/env groovy

pipeline {
    agent any

    triggers {
        upstream(upstreamProjects: "react/simple", threshold: hudson.model.Result.SUCCESS)
    }

    stages {
        stage("React") {
            steps {
                script {
                    def now = new Date()
                    println "Reacted from the upstreams at ${now}"
                }
            }
        }
    }
}

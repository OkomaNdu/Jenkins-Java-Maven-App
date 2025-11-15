#!/usr/bin/env groovy

library identifier: 'Jenkins-Shared-Library@master', retriever: modernSCM(
        [$class: 'GitSCMSource',
        remote: 'https://github.com/OkomaNdu/Jenkins-Shared-Library.git',
        credentialsId: 'GitHub-Credentials'])

def gv

pipeline  {
    agent any
    tools {
         maven 'maven-3.9'
    }
    stages {
        stage("init") {
            steps {
                script{
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                 script{
                     buildJar()
                 }
            }
        }
        stage("build and push image") {
            steps {
               script{
                   buildImage 'ndubuisip/demo-app:jma-3.0'
                   dockerLogin()
                   dockerPush 'ndubuisip/demo-app:jma-3.0'
               }
            }
        }
        stage("deploy") {
            steps {
                script{
                   gv.deployApp()
                }
            }
        }
    }
}
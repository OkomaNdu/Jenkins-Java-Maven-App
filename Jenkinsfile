#!/usr/bin/env groovy

@Library('Jenkins-Shared-Library')
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
        stage("build image") {
            steps {
               script{
                   buildImage 'ndubuisip/demo-app:jma-3.0'
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
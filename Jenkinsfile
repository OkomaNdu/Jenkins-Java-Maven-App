def gv

pipeline {
    agent any
    tools {
         maven 'maven-3.9'
    }
    stages {
        stage('increment version') {
            steps {
                script {
                    echo 'incrementing app version....'
                     sh '''
                         mvn build-helper:parse-version \
                           versions:set -DnewVersion=\\${parsedVersion.majorVersion}.\\${parsedVersion.minorVersion}.\\${parsedVersion.nextIncrementalVersion} \
                           versions:commit
                      '''
                     def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                     def version = matcher[0][1]
                     env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
                }
            }
        }
        stage('test app') {
            steps {
                script{
                    echo 'testing the application...'
                }
            }
        }
        stage('build app') {
            steps {
                script{
                    echo 'building the application..'
                    sh 'mvn clean package'
                }
            }
        }
        stage('build image') {
            steps {
                 script{
                      echo "building the docker image..."
                      withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                             sh "docker build -t ndubuisip/demo-app:${IMAGE_NAME} ."
                             sh 'echo $PASS | docker login -u $USER --password-stdin'
                             sh "docker push ndubuisip/demo-app:${IMAGE_NAME}"
                      }
                 }
            }
        }
        stage("deploy") {
            steps {
                script {
                   echo 'deploying the application...'
                }
            }
        }

        stage('commit version update') {
            steps {
                script {
                   withCredentials([usernamePassword(credentialsId: 'GitHub-Credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                      sh 'git config --global user.email "ndu2okoma@gmail.com"'
                      sh 'git config --global user.name "OkomaNdu"'

                      sh 'git status'
                      sh 'git branch'
                      sh 'git config --list'


                      sh 'git remote set-url origin https://${USER}:${PASS}@github.com/OkomaNdu/Jenkins-Java-Maven-App.git'
                      sh 'git add .'
                      sh 'git commit -m "ci: version bump"'
                      sh 'git push origin HEAD:Jenkins-Jobs'
                   }
                }
            }
        }
    }
}
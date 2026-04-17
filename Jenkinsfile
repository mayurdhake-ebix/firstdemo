pipeline {
  agent any

  environment {
    DOCKER_IMAGE = 'mayurdhake/firstdemo'
    DOCKER_TAG = "${BUILD_NUMBER}"
  }

  stages {

    stage('Checkout') {
      steps {
        git branch: 'main',
            url: 'https://github.com/mayurdhake-ebix/firstdemo.git'
      }
    }

    stage('Build & Test') {
      steps {
        bat 'C:\\apache-maven-3.9.9\\bin\\mvn.cmd clean test'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('Docker Build') {
      steps {
        bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" build -t %DOCKER_IMAGE%:%DOCKER_TAG% .'
        bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" tag %DOCKER_IMAGE%:%DOCKER_TAG% %DOCKER_IMAGE%:latest'
      }
    }

    stage('Docker Push') {
      steps {
        withCredentials([usernamePassword(
            credentialsId: 'dockerhub-creds',
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )]){
            bat """
            "C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" login -u %DOCKER_USER% -p %DOCKER_PASS%
            "C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" push %DOCKER_IMAGE%:%DOCKER_TAG%
            "C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" push %DOCKER_IMAGE%:latest
            """
        }
      }
    }

    stage('Deploy') {
      steps {
        bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" stop springboot-app || exit 0'
        bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" rm springboot-app || exit 0'
        bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" run -d -p 9090:8080 --name springboot-app %DOCKER_IMAGE%:latest'
      }
    }
  }

  post {
    success { echo 'Pipeline succeeded!' }
    failure { echo 'Pipeline failed — check logs!' }
  }
}

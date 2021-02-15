pipeline {
  agent any
  stages {
    stage('build & test') {
      steps {
        sh 'mvn -DskipTests clean package'
      }
    }

  }
}
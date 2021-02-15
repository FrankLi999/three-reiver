pipeline {
  agent {
    node {
      label 'docker'
    }

  }
  stages {
    stage('build & test') {
      agent {
        node {
          label 'docker'
        }

      }
      steps {
        sh 'mvn -DskipTests clean package'
      }
    }

  }
}
pipeline {
  agent {
    kubernetes {
      cloud 'kubernetes'
      label 'jnlp-slave'
      defaultContainer 'maven'
      idleMinutes 10
      inheritFrom 'jenkins-slave-nodejs'
    }

  }
  stages {
    stage('init-container') {
      steps {
        echo '111'
      }
    }

  }
}
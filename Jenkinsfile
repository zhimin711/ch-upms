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
    stage('build') {
      steps {
        container(name: 'gradle', shell: 'gradle clean bootJar')
      }
    }

  }
}
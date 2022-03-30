pipeline {
  agent {
    kubernetes {
      cloud 'kubernetes'
      label 'jnlp-slave'
      defaultContainer 'docker'
      idleMinutes 10
      inheritFrom 'jenkins-slave-gradle'
    }

  }
  stages {
    stage('build') {
      steps {
        container(name: 'gradle') {
          sh 'gradle clean bootJar'
        }

      }
    }

  }
}
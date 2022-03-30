pipeline {
  agent {
    kubernetes {
      cloud 'kubernetes'
      label 'jnlp-slave'
      defaultContainer 'docker'
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
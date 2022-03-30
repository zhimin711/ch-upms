pipeline {
  agent {
    kubernetes {
      cloud 'kubernetes'
      label 'jnlp-slave'
      idleMinutes 5
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
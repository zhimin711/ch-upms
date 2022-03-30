pipeline {
  agent {
    kubernetes {
      cloud 'kubernetes'
      label 'jnlp-slave'
      defaultContainer 'maven'
      idleMinutes 10
      yamlFile 'jenkins/jenkins_pod_template.yaml'
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
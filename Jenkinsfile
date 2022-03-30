pipeline {
  agent none
  stages {
    stage('init-container') {
      steps {
        echo '111'
        podTemplate(activeDeadlineSeconds: 5, cloud: 'kubernetes')
      }
    }

  }
}
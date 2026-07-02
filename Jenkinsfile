pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    parameters {
        booleanParam(name: 'RUN_SONAR', defaultValue: true, description: 'Ejecutar analisis SonarQube.')
    }

    environment {
        SONAR_PROJECT_KEY = 'com.espigapedidos:espigapedidos'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn --batch-mode clean compile'
            }
        }

        stage('Test and Coverage') {
            steps {
                sh 'mvn --batch-mode -Pcoverage-100 verify -Dspring.profiles.active=test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/site/jacoco/**/*,target/surefire-reports/**/*,target/*.jar'
                }
            }
        }

        stage('SonarQube Analysis') {
            when {
                expression { return params.RUN_SONAR }
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn --batch-mode sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml'
                }
            }
        }

        stage('Quality Gate') {
            when {
                expression { return params.RUN_SONAR }
            }
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts allowEmptyArchive: true, artifacts: 'docs/entregables/**/*,docs/*.md,postman/**/*,qa/**/*,scripts/**/*'
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado exitosamente'
        }
        failure {
            echo 'Pipeline fallo. Revise la consola de Jenkins y los reportes archivados.'
        }
    }
}

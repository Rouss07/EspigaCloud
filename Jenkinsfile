pipeline {
    agent any
    tools { jdk 'JDK17'; maven 'Maven3' }
    environment { SONAR_PROJECT_KEY = 'com.espigapedidos:espigapedidos' }
    stages {
        stage('Checkout') { steps { checkout scm } }
        stage('Compile') { steps { sh 'mvn --batch-mode clean compile' } }
        stage('Test and Coverage') {
            steps { sh 'mvn --batch-mode verify -Dspring.profiles.active=test' }
            post { always {
                junit allowEmptyResults: false, testResults: 'target/surefire-reports/*.xml'
                recordCoverage tools: [[parser: 'JACOCO', pattern: 'target/site/jacoco/jacoco.xml']]
            } }
        }
        stage('SonarQube Analysis') {
            steps { withSonarQubeEnv('SonarQube') {
                sh 'mvn --batch-mode sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml'
            } }
        }
        stage('Quality Gate') {
            steps { timeout(time: 10, unit: 'MINUTES') { waitForQualityGate abortPipeline: true } }
        }
        stage('Archive') {
            steps { archiveArtifacts artifacts: 'target/*.jar,target/site/jacoco/**', fingerprint: true }
        }
    }
    post {
        always { cleanWs deleteDirs: true }
        success { echo 'Pipeline ejecutado exitosamente' }
        failure { echo 'Pipeline falló; revise las etapas y reportes publicados.' }
    }
}

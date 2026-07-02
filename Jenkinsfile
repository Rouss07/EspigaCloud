pipeline {
    agent any
<<<<<<< HEAD
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
=======

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    parameters {
        string(name: 'COVERAGE_MINIMUM', defaultValue: '0.80', description: 'Cobertura minima JaCoCo/Sonar. Usar 1.00 para exigir 100%.')
        string(name: 'DEPLOY_PORT', defaultValue: '8085', description: 'Puerto publicado para el ambiente de pruebas.')
        string(name: 'APP_BASE_URL', defaultValue: 'http://localhost:8085', description: 'URL usada por Postman, Playwright, ZAP y k6.')
        booleanParam(name: 'RUN_E2E', defaultValue: true, description: 'Ejecutar pruebas E2E Selenium con docker-compose.test.yml.')
        booleanParam(name: 'RUN_POSTMAN', defaultValue: true, description: 'Ejecutar pruebas integrales Postman/Newman.')
        booleanParam(name: 'RUN_PLAYWRIGHT', defaultValue: true, description: 'Ejecutar pruebas EndToEnd Playwright.')
        booleanParam(name: 'RUN_ZAP', defaultValue: true, description: 'Ejecutar OWASP ZAP baseline.')
        booleanParam(name: 'RUN_K6', defaultValue: true, description: 'Ejecutar smoke tests de rendimiento k6.')
    }

    environment {
        SONAR_PROJECT_KEY = 'com.espigapedidos:espigapedidos'
        DOCKER_IMAGE = 'espigapedidos'
        ADMIN_PASSWORD = credentials('admin-password')
        TIENDA_PASSWORD = credentials('tienda-password')
        REPORT_DIR = 'target/quality-reports'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Unitarias + Integrales + Cobertura') {
            steps {
                sh 'mvn -B verify -Dspring.profiles.active=test -Dcoverage.minimum=${COVERAGE_MINIMUM}'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                    jacoco execPattern: 'target/jacoco.exec', classPattern: 'target/classes', sourcePattern: 'src/main/java'
                    publishHTML allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'target/site/jacoco', reportFiles: 'index.html', reportName: 'JaCoCo Coverage'
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/site/jacoco/**/*,target/surefire-reports/**/*'
                }
            }
        }

        stage('SonarQube Analysis + Quality Gate') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn -B sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.qualitygate.wait=true'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'DOCKER_BUILDKIT=0 docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} -t ${DOCKER_IMAGE}:latest .'
            }
        }

        stage('Ambiente de Pruebas') {
            steps {
                sh 'DEPLOY_PORT=${DEPLOY_PORT} ADMIN_PASSWORD=${ADMIN_PASSWORD} TIENDA_PASSWORD=${TIENDA_PASSWORD} docker compose up -d --build'
                sh './scripts/ci/wait-for-url.sh ${APP_BASE_URL}/login'
            }
        }

        stage('E2E Selenium') {
            when { expression { return params.RUN_E2E } }
            steps {
                sh 'docker compose -f docker-compose.test.yml run --rm tests'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/failsafe-reports/*.xml'
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/failsafe-reports/**/*'
                }
            }
        }

        stage('Postman Parametros Entrada') {
            when { expression { return params.RUN_POSTMAN } }
            steps {
                sh './scripts/ci/run-postman.sh ${APP_BASE_URL}'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/postman/newman-report.xml'
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/postman/**/*'
                }
            }
        }

        stage('Playwright E2E') {
            when { expression { return params.RUN_PLAYWRIGHT } }
            steps {
                sh './scripts/ci/run-playwright.sh ${APP_BASE_URL}'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/playwright/results.xml'
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/playwright/**/*'
                }
            }
        }

        stage('OWASP ZAP Seguridad') {
            when { expression { return params.RUN_ZAP } }
            steps {
                sh './scripts/ci/run-zap-baseline.sh ${APP_BASE_URL}'
            }
            post {
                always {
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/security/**/*'
                    publishHTML allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'target/security', reportFiles: 'zap-report.html', reportName: 'OWASP ZAP'
                }
            }
        }

        stage('k6 Rendimiento') {
            when { expression { return params.RUN_K6 } }
            steps {
                sh './scripts/ci/run-k6-suite.sh ${APP_BASE_URL}'
            }
            post {
                always {
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/k6/**/*'
                }
            }
>>>>>>> 723b50d (Actualización)
        }
    }
    post {
<<<<<<< HEAD
        always { cleanWs deleteDirs: true }
        success { echo 'Pipeline ejecutado exitosamente' }
        failure { echo 'Pipeline falló; revise las etapas y reportes publicados.' }
=======
        always {
            archiveArtifacts allowEmptyArchive: true, artifacts: 'docs/entregables/**/*,docs/*.md,postman/**/*,qa/**/*'
        }
        success {
            echo 'Pipeline ejecutado exitosamente. Revisar JaCoCo, SonarQube, Postman, Playwright, ZAP y k6.'
        }
        failure {
            echo 'Pipeline fallo. Revisar reportes archivados y Quality Gate.'
        }
>>>>>>> 723b50d (Actualización)
    }
}

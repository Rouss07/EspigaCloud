pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    parameters {
        string(name: 'DEPLOY_PORT', defaultValue: '8085', description: 'Puerto del ambiente temporal de pruebas.')
        booleanParam(name: 'RUN_PLAYWRIGHT', defaultValue: true, description: 'Ejecutar pruebas funcionales Playwright.')
        booleanParam(name: 'RUN_K6', defaultValue: true, description: 'Ejecutar pruebas de rendimiento k6.')
        booleanParam(name: 'RUN_SONAR', defaultValue: true, description: 'Ejecutar analisis SonarQube.')
    }

    environment {
        SONAR_PROJECT_KEY = 'com.espigapedidos:espigapedidos'
        APP_BASE_URL = "http://localhost:${params.DEPLOY_PORT}"
        COMPOSE_PROJECT_NAME = "espigacloud-ci-${BUILD_NUMBER}"
        ADMIN_PASSWORD = 'Admin123*'
        TIENDA_PASSWORD = 'Tienda123*'
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Install Dependencies') {
            steps {
                sh 'mvn --batch-mode dependency:go-offline'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn --batch-mode -Pcoverage-100 clean verify -Dspring.profiles.active=test'
            }
            post {
                always {
                    junit allowEmptyResults: false, testResults: 'target/surefire-reports/*.xml'
                    archiveArtifacts allowEmptyArchive: false, artifacts: 'target/site/jacoco/**/*,target/surefire-reports/**/*'
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn --batch-mode -DskipTests package'
                sh 'docker compose build app'
            }
        }

        stage('Test Environment') {
            steps {
                sh 'DEPLOY_PORT=${DEPLOY_PORT} ADMIN_PASSWORD=${ADMIN_PASSWORD} TIENDA_PASSWORD=${TIENDA_PASSWORD} docker compose up -d mysql app'
                sh './scripts/ci/wait-for-url.sh ${APP_BASE_URL}/login'
            }
        }

        stage('Install Playwright Browser') {
            when { expression { return params.RUN_PLAYWRIGHT } }
            steps {
                sh 'docker pull mcr.microsoft.com/playwright:v1.45.0-jammy'
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

        stage('K6 Performance') {
            when { expression { return params.RUN_K6 } }
            steps {
                sh './scripts/ci/run-k6-suite.sh ${APP_BASE_URL}'
            }
            post {
                always {
                    archiveArtifacts allowEmptyArchive: true, artifacts: 'target/k6/**/*'
                }
            }
        }

        stage('SonarQube Analysis') {
            when { expression { return params.RUN_SONAR } }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn --batch-mode sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml'
                }
            }
        }

        stage('Quality Gate') {
            when { expression { return params.RUN_SONAR } }
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Archive Build') {
            steps {
                archiveArtifacts fingerprint: true, artifacts: 'target/*.jar,docs/entregables/**/*,docs/*.md'
            }
        }
    }

    post {
        always {
            sh 'docker compose down --remove-orphans || true'
        }
        success {
            echo 'Pipeline exitoso: pruebas, build, Playwright, k6, SonarQube y Quality Gate completados.'
        }
        failure {
            echo 'Pipeline fallo. Revise la etapa y los reportes archivados.'
        }
    }
}

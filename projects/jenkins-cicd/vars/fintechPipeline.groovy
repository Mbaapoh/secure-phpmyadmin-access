def call(Map config) {
    pipeline {
        agent any
        
        environment {
            PROJECT_NAME = "${config.projectName}"
            SONAR_KEY = "${config.sonarKey}"
            SERVICE_DIR = "${config.serviceDir}"
        }
        
        stages {
            stage('Checkout') {
                steps {
                    checkout scm
                }
            }
            
            stage('Unit Tests') {
                steps {
                    dir("${SERVICE_DIR}") {
                        script {
                            // If it's a Go project, run go test.
                            // We can use a docker container to run the tests to avoid needing Go installed on the Jenkins agent.
                            if (fileExists('go.mod')) {
                                sh '''
                                    docker run --rm -v "$(pwd):/app" -w /app golang:1.22 go test -v ./...
                                '''
                            } else {
                                echo "No tests configured for this project type."
                            }
                        }
                    }
                }
            }
            
            stage('Static Code Analysis (SonarQube)') {
                steps {
                    dir("${SERVICE_DIR}") {
                        withSonarQubeEnv('sonar-server') {
                            // Using the SonarScanner tool installed via JCasC
                            script {
                                def scannerHome = tool 'sonar-scanner'
                                sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=${SONAR_KEY} -Dsonar.projectName='${PROJECT_NAME}'"
                            }
                        }
                    }
                }
            }
        }
    }
}



pipeline {
    agent any
    stages {  
        stage('SCM') {
            steps {
                git branch: 'main', url: 'https://github.com/alepaco-maton/API-Gestion-de-clientes-y-cuentas.git'
            }
        } 
        stage('Build & Test') { 
            steps {
                powershell 'mvn -B  clean install'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'  
                }
                
            }

        }
        stage('dependencyCheck') {
            steps {
                dependencyCheck additionalArguments: '''--format XML --format HTML''', odcInstallation: 'owasp'
                dependencyCheckPublisher pattern: 'dependency-check-report.xml'
            }
        }
        stage('reporte de covertura') {
            steps {
                jacoco()
                publishCoverage adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')], checksName: '', sourceFileResolver: sourceFiles('NEVER_STORE')
            }
        }  
        stage("SonarQube analysis") { 
            steps { 
              withSonarQubeEnv(installationName: 'sonarqubelocal', credentialsId: 'sonarqubecredentail') {
                powershell 'xcopy  C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\API-Gestion-de-clientes-y-cuentas  . /E /I /Y'
                powershell 'mvn sonar:sonar'
              }
            }
        } 
        stage('Deploy Test') {
            steps { 
                powershell 'Copy-Item -Path "target\\pruebatecnica-0.0.1-SNAPSHOT.war" -Destination "c:\\xamp\\tomcat\\webapps"  -Force'
            }
        }
        stage('Test integration') {
            steps {
                powershell 'mvn integration-test'
            }
        }

    } 
}

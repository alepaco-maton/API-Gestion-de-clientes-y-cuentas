pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                powershell 'Write-Output  "Credential sonarqube : '+credentials('252sonarqubecredentail') + '"'
                powershell 'mvn -B -DskipTests clean package'
            } 
        }
        stage('Test') { 
            steps {
                powershell 'mvn test' 
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
    } 

}

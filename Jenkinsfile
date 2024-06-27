

pipeline {
    agent any
    stages {  
        stage("SCM") {
            steps {
                git branch: 'main', url: 'https://github.com/alepaco-maton/API-Gestion-de-clientes-y-cuentas.git'
            }
        }
        stage('Build') {
            steps {
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

         stage('Build/Test') {
            steps {
                echo 'Testing - Coverage Test (Unit/IT)'
                sh "mvn clean install"
                echo 'SonarQube'
                withSonarQubeEnv(credentialsId: 'JenkinsTokenSonar', installationName: 'sonarqubeserver') {
                    sh "$SONNAR_HOME/bin/sonar-scanner -Dproject.settings='sonar-project.properties'"
                }
            }
        }
        stage("SonarQube analysis") {
            agent any
            steps { 
              withSonarQubeEnv(installationName: 'sonarqubelocal', credentialsId: 'sonarqubecredentail') {
                powershell 'xcopy  C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\API-Gestion-de-clientes-y-cuentas  . /E /I /Y'
                powershell '''
                    mvn sonar:sonar \
                      -Dsonar.maven.scanAll=true \
                      -Dsonar.language=java \
                      -Dsonar.sources=src/main/java \
                      -Dsonar.java.binaries=. \
                      -Dsonar.sourceEncoding=UTF-8 \
                      -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                      -Dsonar.tests=src/test/java \
                      -Dsonar.dependencyCheck.htmlReportPath=dependency-check-report.html \
                      -Dsonar.java.spotbugs.reportPaths=target/spotbugsXml.xml \
                      -Dsonar.java.checkstyle.reportPaths=target/reports/checkstyle/checkstyle-result.xml
                '''
              }
            }
          }
 
    } 
}

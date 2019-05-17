node {
	def mvnHome
    stage('checkout') {
        checkout scm
		mvnHome = tool 'apache-maven-3.3.9'
    }

    // uncomment these 2 lines and edit the name 'node-4.5.0' according to what you choose in configuration
     def nodeHome = tool name: 'node-4.5.0', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
     env.PATH = "C:/Users/M1024688/AppData/Roaming/npm;${nodeHome};${env.PATH}"
	echo "${env.PATH}"
    stage('check tools') {
        if (isUnix()) {
			sh "node -v"
			sh "npm -v"
			sh "bower -v"
			sh "gulp -v"
		} else {
			bat label: '', script: 'node -v'
			bat label: '', script: 'npm -v'
			bat label: '', script: 'bower -v'
			bat label: '', script: 'gulp -v'
		}
    }

    stage('npm install') {
		if (isUnix()) {
			sh "npm install"
		} else {
			bat label: '', script: 'npm install'
		}
    }

    stage('clean') {
		if (isUnix()) {
			sh "./mvnw clean"
		} else {
			bat label: '', script: './mvnw clean'
		}
    }

    stage('backend tests') {
        try {
			if (isUnix()) {
				sh "./mvnw test"
			}else {
				bat label: '', script: './mvnw test'
			}
        } catch(err) {
			notify("Failed")
            throw err
        } finally {
            step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
        }
    }

   // stage('frontend tests') {
   //     try {
	//		if (isUnix()) {
	//			sh "gulp test"
	//		}else {
	//			bat label: '', script: 'gulp test'
	//		}
   //     } catch(err) {
   //         throw err
    //    } finally {
    //        step([$class: 'JUnitResultArchiver', testResults: '**/target/test-results/karma/TESTS-*.xml'])
    //    }
  //  }
  
	  stage('SonarQube analysis') {
		// requires SonarQube Scanner 2.8+
		def scannerHome = tool 'sonar';
		withSonarQubeEnv('sonar') {
			if (isUnix()) {
				sh script: "${scannerHome}/bin/sonar-scanner"
			} else {
				//using sonar-scanner which requires sonar.properties file in the project path
				//bat script: "${scannerHome}/bin/sonar-scanner"
				bat script: "${mvnHome}/bin/mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar"				
			}
		}
	  }
	  
	  stage("Quality Gate") {
		withSonarQubeEnv('sonar') {
			// Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
			// true = set pipeline to UNSTABLE, false = don't
			// Requires SonarQube Scanner for Jenkins 2.7+
			//waitForQualityGate abortPipeline: true
			
			def qg = waitForQualityGate() 
			if (qg.status != 'OK') {
				error "Pipeline aborted due to quality gate failure: ${qg.status}"
			} 
		}
	}
	
	 //Package creating
    stage('packaging') {
		if (isUnix()) {
			sh "./mvnw package -Pprod -DskipTests"
		} else {
			// bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
			bat script: "./mvnw package -Pprod -DskipTests"
		}
    }
	
	stage ('Artifactory configuration') {
		
		rtMavenResolver id: "MAVEN_RESOLVER", serverId: 'Artifactory-local', releaseRepo: 'maven-release', snapshotRepo: 'maven-virtual'
	
		rtMavenDeployer id: "MAVEN_DEPLOYER",serverId: 'Artifactory-local', releaseRepo: 'maven-release-local', snapshotRepo: 'maven-local'

		rtMavenRun (
			tool: 'apache-maven-3.3.9', // Tool name from Jenkins configuration
			pom: 'pom.xml',
			goals: 'clean install',
			deployerId: "MAVEN_DEPLOYER",
			resolverId: "MAVEN_RESOLVER"
		)
		rtPublishBuildInfo (
			serverId: "Artifactory-local"
		)			
		
	}
}

//Mail notification
def notify(status) {
  // wrap([$class: 'BuildUser']) {
       emailext (
		   subject: "${status}: Job ${env.JOB_NAME} ([${env.BUILD_NUMBER})",
		   body: """
		   Check console output at <a href="${env.BUILD_URL}">${env.JOB_NAME} (${env.BUILD_NUMBER})</a>""",
		   //to: "${BUILD_USER_EMAIL}", 
		   to: "shubha.srinivasara@mindtree.com",
		   from: 'Mindtree.ATLAS@mindtree.com'
	   )
   //}
}
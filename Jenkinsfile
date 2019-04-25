node {
	def mvnHome
    stage('checkout') {
        checkout scm
		mvnHome = tool 'apache-maven-3.3.9'
    }

    // uncomment these 2 lines and edit the name 'node-4.5.0' according to what you choose in configuration
     def nodeHome = tool name: 'node-4.5.0', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
     env.PATH = "${nodeHome}/bin;${env.PATH}"
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
            throw err
        } finally {
            step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
        }
    }

    stage('frontend tests') {
        try {
            sh "gulp test"
        } catch(err) {
            throw err
        } finally {
            step([$class: 'JUnitResultArchiver', testResults: '**/target/test-results/karma/TESTS-*.xml'])
        }
    }

    stage('packaging') {
		if (isUnix()) {
			sh "./mvnw package -Pprod -DskipTests"
		} else {
			bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
		}
    }
}

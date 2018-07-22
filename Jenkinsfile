#!groovy
node('') {
    def gitCommitId = null          // filled in Compile & test stage
    def buildTimestamp = null
    // locationserviceworkshop must match a valid docker hub account
    def dockerImageId = "locationserviceworkshop/location-service"
    def mavenImage = docker.image('maven:3.5.2-jdk-8')
    def postgresImage = docker.image('postgres:9.6')
    def locationServiceImage = null

    stage('compile & unit-test') {
        gitCommitId = checkout(scm).GIT_COMMIT.substring(0, 7)
        // Workaround to shut up stupid script security, would've rather used: ZonedDateTime.now(ZoneOffset.UTC).toString()
        buildTimestamp = sh(script: 'date -u +%Y-%m-%dT%H:%M:%S%Z | head -c 22', returnStdout: true)

        dir('location-service') {
            mavenImage.inside("--entrypoint=''") {
                // disable the entrypoint in the maven container, we run our own command
                sh "mvn clean verify"
            }
            junit '**/target/surefire-reports/*.xml'
        }
    }

    stage('build docker image') {
        locationServiceImage = docker.build "${dockerImageId}:${gitCommitId}", "--build-arg git_commit_id=${gitCommitId} --build-arg build_timestamp=${buildTimestamp} -f location-service/Dockerfile location-service"
    }

    stage('system tests') {
        postgresImage.withRun("-e POSTGRES_DATABASE=locationservice " +
                " -e POSTGRES_USER=locationservice " +
                " -e POSTGRES_PASSWORD=locationservice") { db ->

            locationServiceImage.withRun("--link ${db.id}:db" +
                    " -e DB_URL=jdbc:postgresql://db/locationservice") { service ->
                // --link adds an entry into the /etc/hosts file and maps *db* to the IP address of the postgres container

                mavenImage.inside("--link ${service.id}:service" +
                        " -e LOCATION_SERVICE_ADDRESS=service:8080" +
                        " --entrypoint='' ") {
                    // --link adds an entry into the /etc/hosts file and maps *service* to the IP address of the postgres container

                    sh "cd location-service-system-test && mvn clean verify"
                }
            }
        }
        junit '**/target/surefire-reports/*.xml'
    }

    if (env.BRANCH_NAME == 'master') {
        stage('push image to docker hub') {
            locationServiceImage.push()
            // also tag and push as latest. so we have an easy handle on the latest produced image
            locationServiceImage.push("latest")
        }

        stage('deploy to accept') {
            workspace = pwd()
            // the repo in /home/ubuntu has be configured with Amazon EC2 access keys, therefore we use this path here
            // normally this is stored in a separate repository called /platform, to which all deploy scripts have access.
            sh "ansible-playbook -i /home/ubuntu/location-service-workshop/aws/ansible/staging -e service_version=${gitCommitId} location-service-deployment/deploy-app-with-database.yml"
        }
    }
}

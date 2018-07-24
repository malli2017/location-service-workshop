# Assignment 5 Create a CI/CD pipeline

 1. This assignment requires that you've completed [assignment 2](assignment-2-install-docker-jenkins.md)
    In step   
        
 1. Now open Jenkins. Goto the buildserver IP address in the browser with port number 8080
    
         username: admin
         password: admin
         
         Click on Install suggested plugins
         Click finish (do not edit IP address jenkins suggest)
  
 1. Now fork [toefel18/location-service-workshop](https://github.com/toefel18/location-service-workshop) with your 
    personal github account. (the fork button is on the top-right)
  
 1. In Jenkins, create a multibranch pipeline that builds your forked repository.
 
         Click on *New Item*
 
         Name: location-service-workshop-pipeline
         Type: Multi-branch pipeline
         Click OK

         Scroll to Branch Sources
         Click on add source and select Github
         Add credentials and select 'Jenkins' (otherwise we hit the github rate limiter)
           -> Enter your personal github username and password, and enter github in the id field
              If you do not add credentials, you will hit the github anonymous api rate limiter.
         In the owner field, enter: <the github account that forked>
         In the repository field, enter: location-service-workshop
         
         Scroll to Scan Multibranch Pipeline Triggers
         enable 'Periodically if not otherwise run'
           -> Select 1 minute for time interval.
         This will check github for changes once per minute, 
         and automatically build them :D  
         
         Click save.
         
         Go to the home page by clicking the logo
         Click on the pipeline you created
         Click on master
         
         Wait for master to complete, this will take approx 3-5 minutes.
         Meanwhile, inspect the Jenkinsfile at the root of this repository, which contains
         the instructions Jenkins is currently executing. 
       
 1. Now go to the public IP address of your docker (accept) server `<ip>:8080/version`.
 
 1. Insert a location
 
     ```bash
     curl -X POST -d '{"name": "CGI-Rotterdam", "lat": 51.953326, "lon": 4.5586302}' -H 'Content-Type: application/json' <ip>:8080/v1/locations
     ```
     
     Get the location
     ```bash
     curl <ip>:8080/v1/locations/CGI-Rotterdam
     ```
         
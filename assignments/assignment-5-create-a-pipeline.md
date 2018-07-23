# Assignment 5 Create a CI/CD pipeline

 1. This assignment requires that you've completed [assignment 2](assignment-2-install-docker-jenkins.md)
    In step   
        
 2. Now open Jenkins. Goto the buildserver IP address in the browser with port number 8080
    
         username: admin
         password: admin
         
         Click on Install selected plugins
         Leave the defaults + select the jUnit plugin
         
         Click Install, wait for the installation to complete
         Click finish (do not edit IP address jenkins suggest)
  
 3. Now fork [toefel18/location-service-workshop](https://github.com/toefel18/location-service-workshop) on github
  
 3. Create a multibranch pipeline on *New Item*
 
         Name: location-workshop-pipeline
         Type: Multi-branch pipeline
         Click OK

         Click on Add Credentials and enter your github username and password 
            -> (otherwise we will hit the rate limiter for anon requests)
            
         In the owner field, enter: <the github account that forked>
         In the repository field, enter: location-service-workshop
         Go down and enable Build periodically, select 1 minute for time interval.
         Click save.
         
         Open the project in Jenkins, click on master and wait for the pipeline to complete.
         
         Once complete, then goto the IP address of **groupx_acc_docker**:8080/version. The IP address
         will be communicated via slack.
         
         
         
    
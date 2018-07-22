# Assignment 1 Connectivity

 1. Within CGI, Ask for a GUEST account or connect to the GROUPINFRA-I network, because SSH access disabled on GROUPINFRA-C
 2. Connect to the buildserver of your group (which is also our ansible control center) using the `workshop_ansiblecc_key` 
    in *aws/aws-setup*.

 3. Connect to the remote server, choose the option you like best: 
 
    - a. connect using ssh (also available in git-bash)
  
          ssh -i aws/aws-setup/workshop_ansiblecc_key ubuntu@${GROUP_ANSIBLE_IP}
  
    - b. connect using putty
    
          start putty and go to **Connection>SSH>Auth**
          select private key for authentication
           -> browse and choose *aws/aws-setup/aws_ansiblecc_key*
          go to **Session** and enter ubuntu@${GROUP_BUILDSERVER_IP}

 4. When connected to the buildserver: cd to the project directory containing the platform scripts 
 
        cd location-service-workshop/aws/ansible
         
 5. Test connectivity with staging servers 
 
        ansible -i staging -m ping allservers
       
        #You should get 2 pong's :)
         
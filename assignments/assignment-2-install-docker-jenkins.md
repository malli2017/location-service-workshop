# Assignment 2 Platform installation

Before we can start using the docker commands, we must install Docker on our servers. We use ansible to install Docker
on multiple servers at once. More details will follow later, for now just execute these steps:

 1. Make sure you are connected to the buildserver, see [connectivity](assignment-1-connectivity.md).
         
 2. Roles are ansible scripts that manage a specific task. We use open-source roles that install docker, java and jenkins.
    All the roles we require are listed in *requirements.yml* and must be downloaded with the command below: 
    
        ansible-galaxy install -r requirements.yml

 3. We are now ready to install our acceptance environment. A complete environment is described in the *site.yml* playbook. 
    We can run this playbook by executing: 
 
        ansible-playbook -i staging site.yml

 4. We will **not** provision any other environment, but it would be as easy as executing the following line to get an 
    identical environment:
 
        ansible-playbook -i <environment-name> site.yml
        
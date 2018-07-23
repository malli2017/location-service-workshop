# Assignment 2 Platform installation

Each group has a set of 2 servers with no software on them. Before we can start using the docker commands, we must 
install Docker on our servers. Later on in the workshop we are also going to use Jenkins, so we are going to install
that as well right now. 

With ansible, we can install everyting at once. More details will follow later, for now just execute these steps:

 1. Make sure you are connected to the buildserver of your group, see [connectivity](assignment-1-connectivity.md).
         
 2. Before we can start, we need to install some ansible roles we depend on. We use open-source roles that install 
    docker, java and jenkins. All the roles we require are listed in *requirements.yml* and must be downloaded with 
    the command below: 
    
        ansible-galaxy install -r requirements.yml

 3. We are now ready to install our acceptance environment. A complete environment is described in the *site.yml* playbook. 
    We can run this playbook by executing: 
 
        ansible-playbook -i staging site.yml

 4. We will **not** provision any other environment, but it would be as easy as executing the following line to get an 
    identical environment:
 
        ansible-playbook -i <environment-name> site.yml
        
Done :)
        
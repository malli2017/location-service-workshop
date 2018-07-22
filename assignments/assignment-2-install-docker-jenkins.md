# Assignment 1 Platform installation

Installing and configuring docker, jenkins, java, etc.

 1. Make sure you are connected to the buildserver, see [connectivity](assignment-1-connectivity.md).
         
 2. Roles are ansible scripts that manage a specific task. We use roles that install docker, java and jenkins. These
    roles are open-source and provided by the community. All the roles we require are listed in *requirements.yml* 
    and must be installed with the command below: 
    
        ansible-galaxy install -r requirements.yml

 3. We are now ready to install our acceptance environment. A complete environment is decribed in the *site.yml* playbook. 
    We can run this playbook against staging and production to get identical environments. Let's install staging: 
 
        ansible-playbook -i staging site.yml

 4. We will **not** provision any other environment, but it would be as easy as:
 
        ansible-playbook -i <environment-name> site.yml
        
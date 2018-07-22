# AWS provisioning & deployments

This directory contains two parts, *aws-setup* and *ansible*. 

- *aws-setup* contains playbooks to request a set of servers on the AWS EC2 cloud and install some basic tools.
- *ansible* contains the playbooks to configure the servers with docker, jenkins, etc.

### Creating servers in amazon

Next steps assume you are working on Linux and you have a working AWS account. 


**Setting up the environment in ansible**
 1. Switch to region Oregon (*us-west-2*). This region supports EKS (Elastic Container Service for Kubernetes).
 2. Create a new IAM (Identity and Access Management) user named *ansible-all* in Amazon AWS, 
    and attach the security policy `AmazonEC2FullAccess`, download the credentials. 
 3. Create another user ansible-read with `AmazonEC2ReadOnlyAccess`, download the credentials. 
 4. Go to Services > EC2 > Key Pairs > click create key pair. Name the key-pair *location-workshop-keypair*. Save the
    .pem file as well, you can access the servers via SSH using this key.     
 5. Install awscli, [see online docs](http://docs.aws.amazon.com/cli/latest/userguide/installing.html)
 
    ```bash
     sudo apt install python-pip
     pip install pip --upgrade
     sudo pip install awscli boto
     ```
    
 6. On the command line, execute: `aws configure` and configure the credentials of the account 
    with the credentials of ansible-all (which has AmazonEC2FullAccess rights), set region to *us-west-2* 
    and output format to json.
 7  Disable host key checking by `export ANSIBLE_HOST_KEY_CHECKING=False`
 8. Automatically create and start nodes in Amazon EC2 for each group using Ansible using the commands below. 
    Open provistion-workshop and comment the groups you don't want to create: 
  
    ```bash 
    cd aws-setup
    ansible-playbook provision-workshop.yml"
    ```
    
 9. We now have a bunch of servers, each with their unique tags. Now we need configure the servers of each group so 
    that they can find each other by writing the access key and appropriate tag info in the inventories of each group's 
    buildserver . Wait a while for cloud-init to complete (takes approx 2 minutes, for certainty check the 
    `/var/log/cloud-init.log` on a buildserver). Then fill in the AmazonEC2ReadOnlyAccess account credentials and execute: 
 
    ```bash
    # We must use the workshop private key to connect to the servers, this is the only way in!
    ssh-agent bash
    ssh-add workshop_ansiblecc_key

    ansible-playbook -i ../ansible/production configure-inventories-for-each-group.yml -e "ansible_read_access_key=<READONLY_KEY> ansible_read_secret_key=<READONLY_SECRET>"
    ```
    
 10. Provide each group with the IP addresses of their servers, the pipes strip away unnecessary info:
 
    ```bash
    ansible-playbook -i ../ansible/production list-all-workshop-ip-addresses.yml | grep msg | sort | cut -c 13- | sed 's/.$//' | tee /tmp/ip.txt
    ```

**DONE BY STUDENTS**

 1. (within CGI) Connect to the GROUPINFRA-I network, because SSH access disabled on GROUPINFRA-C
 2. Connect to the buildserver of your group (which is also our ansible control center) using the `workshop_ansiblecc_key` in aws-setup

  *connect using ssh*
  
  ```bash
    ssh -i aws/aws-setup/workshop_ansiblecc_key ubuntu@${GROUP_ANSIBLE_IP}
  ```
  
  *connect using putty*
    * start putty and go to **Connection>SSH>Auth**
    * select private key for authentication
       - browse and choose *aws-setup/aws_ansiblecc_key*
    * go to **Session** and enter ubuntu@${GROUP_BUILDSERVER_IP}

 3. When connected to the buildserver: cd to the project directory containing the platform scripts ```cd location-service-workshop/aws/ansible```
 4. Test connectivity with staging servers ```ansible -i staging -m ping allservers```
 5. Test connectivity with production servers ```ansible -i production -m ping allservers```
 6. Roles are ansible scripts that manage a specific task. We use roles that install docker, java and jenkins. These
    roles are provided by the community. All the roles we require are listed in *requirements.yml* and must be installed
    with the command below: 
    
        ansible-galaxy install -r requirements.yml

 7. We are now ready to install our acceptance environment. A complete environment is decribed in the *site.yml* playbook. 
    We can run this playbook against staging and production to get identical environments. Let's install staging: 
 
        ansible-playbook -i staging site.yml

 8. Now install production as well
 
        ansible-playbook -i production site.yml
        
 9. Now open Jenkins. Goto the buildserver IP address in the browser with port number 8080
    
         username: admin
         password: admin
         
         Click on Install selected plugins
         Leave the defaults + select the jUnit plugin
         
         Click Install, wait for the installation to complete
         Click finish (do not edit IP address jenkins suggest)
          
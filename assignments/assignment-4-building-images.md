# Assignment 4 Building images

### Let's build a custom image

1. First, create a sample application consisting only of a bash script. 

   Let's create a directory first
   
       mkdir /tmp/app
       cd /tmp/app
       
   Create a file `my-app.sh` with the contents
   
       echo "hi fella $1"
   
   Make that file executable 
   
       chmod +x my-app.sh
   
   Test it with 
   
       ./my-app.sh
       ./my-app.sh docker
       
1. Create a file named `Dockerfile` with the following contents:
       
       # extend the bash image
       FROM bash
       
       # copy the my-app.sh inside the image to path /var/my-app.sh
       ADD my-app.sh /var/my-app.sh
       
       # the bash process start our script
       ENTRYPOINT ["bash", "/var/my-app.sh"]
       
       # this are the default arguments passed to our script
       # they can be overridden using docker run
       CMD ["docker"]
     
   Tip, use vim or nano to open a text editor.
   
1. Building the dockerfile, and tag it as 'my-app:latest'
 
       docker build -t my-app:latest .
       
       docker image ls
       
       # `--rm` deletes the container immediatelly after it has exited. 
       # `-it` opens and interactive terminal
       docker run --rm -it my-app
       
       # now run it again, but override the CMD section
       docker run --rm -it my-app dev-community


### Image with environment variables

It is usually safer and more practical to use environment variables instead of
command line arguments to pass parameters. They are OS agnostic.

1. Create a new directory somewhere in temp, just like the previous exercise

1. Create a bash script that prints the environment variable MY_NAME.
 
1. Create a Dockerfile that that runs that script


### Optional: Packaging a Java application

This exercise can only be done from the build-server.

1. Install maven

       sudo apt install -y maven

       cd /home/ubuntu/location-service-workshop/location-service
       mvn clean verify
       
1. Build the java app

       docker build -t java-app:new .
       
1. What do you see when you try to run it?
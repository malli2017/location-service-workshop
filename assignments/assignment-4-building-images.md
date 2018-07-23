# Assignment 4 Building images

### Let's build a custom image

1. First, create a sample application consisting only of
   a bash script. 
   
       mkdir /tmp/app
       cd /tmp/app
       
       # type this manually, DO NOT COPY!
       cat > my-app.sh
       echo "hi fella $1"
       
       to save, hit: CTRL + D
       chmod +x my-app.sh
       test it with:
       ./my-app.sh
       ./my-app.sh docker
       
1. Create a Dockerfile with `vim Dockerfile`, and press the insert key to enter
   edit mode, then enter:
       
       # extend the bash image
       FROM bash
       
       # copy the my-app.sh inside the image to path /var/my-app.sh
       ADD my-app.sh /var/my-app.sh
       
       # the bash process start our script
       ENTRYPOINT ["bash", "/var/my-app.sh"]
       
       # this are the default arguments passed to our script
       # they can be overridden using docker run
       CMD ["docker"]
       
   Hit escape followed by `:x`.
   
   You should now have a file named Dockerfile with the exact contents
   
1. Building the dockerfile

       docker build -t my-app .
       
       docker image ls
       
       # `--rm` deletes the container immediatelly after it has exited. 
       # `-it` opens and interactive terminal
       docker run --rm -it my-app
       
       # now run it again, but override the CMD section
       docker run --rm -it my-app dev-community
# Assignment 3 Playing with docker

See the [presentation](https://toefel18.github.io/location-workshop-presentation/) for examples.

You can look around at [https://hub.docker.com/explore/](https://hub.docker.com/explore/) for all publicly available images. 

### Hello world

1. View all the available images on your system

       docker image ls 
       
1. Download the hello-world image

       docker pull hello-world
       
       # see that the image is now in the list
       docker images
       
1. Run the hello world image

       docker run hello-world
       
### Running a simple service

Redis is a simple key-value service ( it can do a lot more :) )
 commonly used as a cache. Let's do some tests with it.

1. Start redis as a service (services are called daemons in the linux world) by providing `-d`.
   This time we also give it a name so we have an easy reference to it.

       docker run -d --name cgi-redis redis
   
1. View the running container:
       
       docker container ls          (or docker ps, for short)
   
       CONTAINER ID  IMAGE  COMMAND                 CREATED        STATUS        PORTS     NAMES
       f0b417e59fb7  redis  "docker-entrypoint.s…"  7 seconds ago  Up 7 seconds  6379/tcp  eager_villani

       You'll see that there is a container running from the image
       'redis'. It's got an id and an auto-generated name. 

1. Stop the redis container, all data will be persisted, and available on next start.

       docker stop [container-id or name]    (or use it's container id)

       # this will only show the running containers
       docker container ls

       # this shows all containers 
       docker container ls -a     
      
1. Start the redis container again

       docker start [container-id or name] 

       docker container ls
       
1. Remove the container

       # it needs to be stopped first:
       docker stop [container-id or name]
       docker rm [container-id or name]

### interacting with a container by mapping it's port

Redis runs on a port on which we could access it. But this port is not exposed the host. 
We can map the port from the host OS to the container by providing a `-p <host-port>:<container-port>`

1. Read the redis container documentation on [docker hub/_/redis](https://hub.docker.com/_/redis/) and
   figure out which port redis exposes.
   
1. Run a redis image with name `public-redis` and map the port using `-p`.

       # the port mapping is visible in 
       docker container ls

1. Let's interact with redis!

       sudo ant install -y redis-tools
       
       redis-cli -h localhost -p <host-port>
       
       set hello world
       get hello
       ....
       
       # Hit Ctrl + D to exit!
       
1. To view the logs of the redis container

       docker logs public-redis
       
1. Delete the container while it's still running

       docker rm -f public-redis
       
### Running a webserver (using a volume mapping)

For this exercise we are going to use nginx to serve some HTTP content.

First let's get a gif image to serve as content:

    mkdir -p /tmp/www  &&  cd /tmp/www
    wget https://media.giphy.com/media/8qgPkRRqwkVlC/giphy.gif

1. Follow the specification on [docker hub/_/nginx](https://hub.docker.com/_/nginx/) and
   run an nginx container as a service that serves /tmp/www as static content on public port 3000!
   
1. If you are successful, visit the `<ip-address-of-your-server>:3000` in the browser, and 
   you should see the giphy.
   
1. To get an idea of the processes running inside the container. run

       docker top [nginx-container-id]
       
       
### Tagging an image

Let's say you want to create an alias for the nginx image, you can do that by tagging it. 

    docker tag redis amoeba:current
    
    docker image ls
    # find the image named ameuba
    
    docker image rm amoeba:current
    # removes the alias (not the redis image)
    
We often tag our images with the commit id.
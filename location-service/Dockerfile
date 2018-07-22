# depend on a base image with alpine linux (which is very small) and java 8 installed
# TODO switch to https://hub.docker.com/r/library/openjdk/
FROM anapsix/alpine-java:8

# the service will run on port 8080, so expose it.
EXPOSE 8080

# any metadata can be provided with LABEL instructions and viewed with docker inspect.
LABEL maintainer="github.com/toefel18"

# define build-time arguments, makes it easy to bake some constants into our image
# these arguments can be provided to docker build, for example with:
# docker build --build-arg git_commit_id=some-value ...  
ARG git_commit_id
ARG build_timestamp

# build time arguments are not preserved after the build has finished
# therefore set the build arguments as environment variables so the application can pick them up
ENV GIT_COMMIT $git_commit_id
ENV BUILD_TIMESTAMP $build_timestamp

# copy the produced jar file and it's dependencies into the image to /opt
# spring boot creates a shaded jar, we just use the original jar file!
COPY target/location-service-1.0.0.jar.original /opt/location-service/location-service.jar
COPY target/lib /opt/location-service/lib

# when the users runs a container, it's working directory will be set to /opt/location-service
WORKDIR /opt/location-service

# start our application when the container is started!
# exec is required to make java the root process instead of /bin/sh.
# when processes get shut down, linux sends the SIGTERM signal, received by the root process of the container.
# Java our root process now, so it receives the SIGTERM and can start a graceful shutdown.
CMD exec java -cp location-service.jar:lib/* nl.toefel.location.service.Application
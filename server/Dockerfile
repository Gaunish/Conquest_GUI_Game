FROM openjdk:11
RUN apt-get update
RUN apt-get install -y make 

FROM gradle:jdk11 as gradleimage
ADD --chown=gradle . /server
WORKDIR /server
RUN gradle installDist

CMD ["./build/install/server/bin/server 1234 2"]

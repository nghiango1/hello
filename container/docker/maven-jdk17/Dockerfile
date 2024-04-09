# Use the official Ubuntu 14.04 base image from the Docker Hub
FROM ubuntu:22.04

RUN apt-get update -y
RUN apt-get upgrade -y
RUN apt-get install -y maven openjdk-17-jdk

WORKDIR /workspace/

ENTRYPOINT ["/bin/bash"]

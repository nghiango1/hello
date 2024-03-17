# Use the official Ubuntu 14.04 base image from the Docker Hub
FROM ubuntu:22.04

RUN apt-get update -y
RUN apt-get upgrade -y
RUN apt-get install -y g++

# Set the env variable DEBIAN_FRONTEND to noninteractive
ENV DEBIAN_FRONTEND noninteractive

WORKDIR /arrlen/

COPY arrlen.cpp /arrlen/
COPY array.cpp /arrlen/

RUN mkdir dist
RUN g++ array.cpp -o dist/array -fdiagnostics-plain-output -g -Wall -Wextra
RUN g++ arrlen.cpp -o dist/arrlen -fdiagnostics-plain-output -g -Wall -Wextra

ENTRYPOINT ["dist/arrlen"]

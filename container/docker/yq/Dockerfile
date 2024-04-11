# Use the official Ubuntu 14.04 base image from the Docker Hub
FROM ubuntu:22.04

RUN apt-get update -y
RUN apt-get upgrade -y
RUN apt-get install -y curl

RUN curl -L  https://github.com/mikefarah/yq/releases/latest/download/yq_linux_amd64 -o /usr/bin/yq
RUN chmod +x /usr/bin/yq

ENTRYPOINT ["yq"]

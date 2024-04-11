# Use the official Ubuntu 14.04 base image from the Docker Hub
FROM ubuntu:22.04

RUN apt-get update -y
RUN apt-get upgrade -y
RUN apt-get install -y wget

WORKDIR /workspace/bin
ENV PATH="$PATH:/workspace/bin"
RUN wget -qO yq https://github.com/mikefarah/yq/releases/latest/download/yq_linux_amd64
RUN chmod a+x yq

WORKDIR /workspace
ENTRYPOINT ["/bin/bash"]

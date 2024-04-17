# Docker

`sudo docker` come a long way

## Install docker

Run `docker-repo.sh` in ubuntu server to add the keyring. I can't really said if it up to date though, so I recheck Docker Doc time to time if error happend

## What I have used Docker for

Too quickly install latest application and quickly setup my environment instead of manually install, which affect my normal workspace. Application used:
- `jira/`: A publicly available project management tool. I use private self built program at work though.
- `mysql/`, `postgres/`: Quick database for my application coding and testing
- `fpc/`: I revisit pascal time to time. It fun

To create and craft my own environment, tool, image for personal use:
- `base-ubuntu/`: Use for reference when start a new Ubuntu container
- `maven-jdk17/`: OpenJDK17 and Maven environment as a image (Base Ubuntu)
- `yq/`: Yaml processor program as a image (Base Ubuntu)

Basic tutorial:
- `helloworld/`: Running hello world program image
- `samplecontainer/`: Build a new hello world program using go (Base Go image), set up Entry point to created executable


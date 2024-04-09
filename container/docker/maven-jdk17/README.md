# Maven with Open JDK17 image

To help with build environement for Java project

## Notes and how to use

Build the image

```sh
sudo docker build -t maven-jdk17 .
```

Publish the image to Docker hub (with default docker setting)

```sh
sudo docker tag maven-jdk17 nghiango1/maven-jdk17
sudo docker push nghiango1/maven-jdk17
```

> It assumming that you already done login when using DockerCLI. If you have not, the above publish command will fall and tell you instruction to install it

Output to a local file image, this is useful when you want to build the image in online environment then tranfer it to (for example) offline repo in your local only enviroment

```sh
sudo docker save maven-jdk17 -o maven-jdk17
```

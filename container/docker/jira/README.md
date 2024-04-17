# Jira

Jira is a projet management tools.

## Requirement

It need a volume? Docker have volume?

```sh
sudo docker volume create --name jiraVolume
sudo docker run -v jiraVolume:/var/atlassian/application-data/jira --name="jira" -d -p 8080:12001 atlassian/jira-software
```

## Notes

### Container management with docker

Port forwarding is wrong, and this isn't docker swam so we may need to delete container to change the command option. Eg you running this:

```sh
# sudo docker run -v jiraVolume:/var/atlassian/application-data/jira --name="jira" -d -p 8080:8080 atlassian/jira-software
sudo docker run -v jiraVolume:/var/atlassian/application-data/jira --name="jira" -d -p 12001:12001 atlassian/jira-software
sudo docker run -v jiraVolume:/var/atlassian/application-data/jira --name="jira" -d -p 8080:12001 atlassian/jira-software
```

Error expected:
```
docker: Error response from daemon: Conflict. The container name "/jira" is already in use by container "56f3559fdc4eebac088df918d1a791fccd4bd1c708022768f91aac143f2212ba". You have to remove (or rename) that container to be able to reuse that name.
```

**FIX:**

Remove the container using this command
```sh
sudo docker container rm 56f3559fdc4eebac088df918d1a791fccd4bd1c708022768f91aac143f2212ba
#change the container id ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
```

Rerun the docker run command

### Container management with docker swarm

> This isn't set up docker-compose.yml yet so current notes will only to be reference

When using docker swarm, the confiuration of the running container will be update instead of throwing errors
```sh
sudo docker stack deploy -c docker-compose.yml posgresql
```

Output:
```
Ignoring unsupported options: restart, shm_size

Updating service posgresql_adminer (id: cytilpc9duz0930p92br93sk5)
Updating service posgresql_db (id: x0ykepuviwbspctxtfziajuj1)
```

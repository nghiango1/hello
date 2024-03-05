# CICD

This lab

```
Gitlab -> Jenkins -> ArgoCD -> Openshift
          v                        ^
          Nexus -------------------|
```


Gitlab: Store code, CICD pile line
- CICD pileline will be config as Gitlab webhook config flie inside the repo
    - Build
    - Deploy
        - Build image and push to Nexus registry
        - ArgoCD for deploying command

Jenkins: Intergrate with Gitlab and automatically build code
- Executing Gitlab webhook
    - Jenkins can't target Openshift, to it rely on `ArgoCD` command to deploy
- Have Dashboard to monotor all process, record total runtime for CICD etc

ArgoCD: Build image with built file, send image to Openshift for deploy
- Can target and automized Openshift depoy directly
- Monitor all app that have deploy

Openshift: Automatic manage deployed container image

Nexus: Image registry for Docker/Openshift

## Notes

YAML config file, can config pipe line with seperated ruler. It mean that we can run build partially on where source code change

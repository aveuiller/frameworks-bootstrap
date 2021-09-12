# CI/CD

This CI/CD directory contains scripts used by [.gitlab-ci.yml](../.gitlab-ci.yml),
and building the project [SpringBoot](../SpringBoot/).

## Test Locally

To avoid having to spam Gitlab with commits, 
you can use the pipeline with a local `gitlab-runner` instance.

The following section explains how to do so using docker, based on this very detailed
[StackOverflow answer](https://stackoverflow.com/a/65920577/2564085).

1. Run a gitlab-runner container that can control the docker daemon (to start jobs).
    ```shell
    # Start a sonarqube if you want to check code quality
    $ docker run --name sonarqube -p9001:9000 sonarqube
    
    # Start the gitlab-runner instance
    $ cd frameworks-bootstrap
    $ docker run -d \
      --name gitlab-runner \
      --restart always \
      -v $PWD:$PWD \
      -v /var/run/docker.sock:/var/run/docker.sock \
      gitlab/gitlab-runner:latest
    ```
2. Ask for the execution of the project pipeline
    ```shell
    # Last argument is the name of any job
    $ docker exec -it -w $PWD gitlab-runner gitlab-runner exec docker unittest
    ```

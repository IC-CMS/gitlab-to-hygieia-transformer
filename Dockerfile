FROM openjdk:8-alpine
MAINTAINER David Hessler <davidh.092705@gmail.com>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.jar"]
# Add Maven dependencies (not shaded into the artifact; Docker-cached)
ADD gitlab-to-hygieia-transformer-1.0.jar /usr/share/myservice/myservice.jar
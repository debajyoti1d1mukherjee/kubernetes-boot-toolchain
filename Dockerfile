FROM java:8-alpine
WORKDIR /usr/src
ADD /var/lib/docker/tmp/docker-builder488377708/target/TestCOS-0.0.1-SNAPSHOT.jar TestCOS-0.0.1-SNAPSHOT.jar
EXPOSE 8080
#ENTRYPOINT["java,"-jar","/TestCOS-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","TestCOS-0.0.1-SNAPSHOT.jar"]
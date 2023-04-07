FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=target/favourite-recipes-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} favourite-recipes-service.jar
ENTRYPOINT ["java","-jar","/favourite-recipes-service.jar"]
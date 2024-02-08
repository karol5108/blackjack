# define base docker image
FROM openjdk:17
EXPOSE 8080
ADD target/SecurityPlayApp-0.0.1-SNAPSHOT.jar SecurityPlayApp.jar
ENTRYPOINT ["java", "-jar", "SecurityPlayApp.jar"]
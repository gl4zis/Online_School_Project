FROM openjdk
COPY build/libs/*.jar file.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "file.jar"]
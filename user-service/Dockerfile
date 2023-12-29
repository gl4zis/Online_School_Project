FROM openjdk
COPY build/libs/*.jar user.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "user.jar"]
FROM openjdk
COPY build/libs/*.jar server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "server.jar"]
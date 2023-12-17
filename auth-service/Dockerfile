FROM openjdk
COPY build/libs/*.jar auth.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "auth.jar"]
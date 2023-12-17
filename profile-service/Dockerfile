FROM openjdk
COPY build/libs/*.jar profile.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "profile.jar"]
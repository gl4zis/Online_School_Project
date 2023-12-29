FROM openjdk
COPY build/libs/*.jar course.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "course.jar"]
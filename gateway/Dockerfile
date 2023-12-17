FROM openjdk
COPY build/libs/*.jar gateway.jar
EXPOSE 8765
ENTRYPOINT ["java", "-jar", "gateway.jar"]
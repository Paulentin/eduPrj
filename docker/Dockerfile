FROM maven:3.6-jdk-11 as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package

FROM openjdk:11
COPY --from=builder /app/target/eduPrj*.jar eduPrj.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "eduPrj.jar"]

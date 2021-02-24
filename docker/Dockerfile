FROM maven:3.6-jdk-11 as builder
COPY . /home/maven/src
# Copy local code to the container image.
WORKDIR /home/maven/src

# Build a release artifact.
RUN mvn package

FROM openjdk:11
COPY --from=builder /home/maven/src/target/eduPrj*.jar /app/eduPrj.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/eduPrj.jar"]

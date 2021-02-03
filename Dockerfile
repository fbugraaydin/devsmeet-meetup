FROM openjdk:12-jdk-alpine
COPY meetup-0.0.1-SNAPSHOT.jar meetup-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","meetup-0.0.1-SNAPSHOT.jar"]
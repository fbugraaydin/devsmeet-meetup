FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/fbugraaydin/devsmeet-meetup.git

FROM gradle:6.7.1-jdk11 AS build
WORKDIR /app
COPY --from=clone /app/devsmeet-meetup /app
RUN gradle build

FROM openjdk:12-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/meetup-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java","-jar","meetup-0.0.1-SNAPSHOT.jar"]
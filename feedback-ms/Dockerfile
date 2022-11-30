FROM gradle:7-jdk17 AS build
#Copy source files and compile them
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon --stacktrace

FROM gradle:7-jdk17
EXPOSE 8081
#Copy the jar and run it
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/feedback-ms-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar
ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]

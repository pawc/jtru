FROM maven:3.8.6-openjdk-18 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:18
COPY --from=build /home/app/target/jtru-1.0.jar /usr/src/myapp/app.jar
WORKDIR /usr/src/myapp
ENTRYPOINT ["java","-jar","/usr/src/myapp/app.jar"]
EXPOSE 8080
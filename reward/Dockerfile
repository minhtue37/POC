FROM maven:3.8.7-eclipse-temurin-11

WORKDIR /reward
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
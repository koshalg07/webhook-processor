FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app


COPY pom.xml ./

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S webhook && adduser -S webhook -G webhook

COPY --from=build /app/target/webhook-processor-*.jar app.jar

RUN chown webhook:webhook app.jar

USER webhook

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=15s --retries=3 \
  CMD wget -qO- http://localhost:8080/api/v1/webhooks/payment || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
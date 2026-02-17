FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .

RUN mvn dependency:resolve

COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080 \
    POSTGRES_USER=postgres \
    POSTGRES_PASSWORD=postgres \
    POSTGRES_DB=chat \
    POSTGRES_HOST=localhost \
    POSTGRES_URL=jdbc:postgresql://localhost:5432/chat \
    MONGO_USERNAME=root \
    MONGO_PASSWORD=admin \
    MONGO_DB=chat \
    MONGO_HOST=localhost \
    MONGO_URI=mongodb://root:admin@localhost:27017/chat?authSource=admin \
    JWT_SECRET=chavesecretaaqui \
    JWT_EXPIRATION_MS=900000 \
    CORS_ORIGINS=http://localhost:8000 \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
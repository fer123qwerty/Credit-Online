# Usamos una imagen base de Java
FROM openjdk:17-jdk-slim as builder

# Copiar el código fuente y el archivo pom.xml
COPY . /app
WORKDIR /app

# Construir el proyecto usando Maven
RUN ./mvnw clean package -DskipTests

# Usamos una imagen más ligera para la ejecución de la app
FROM openjdk:17-jre-slim

# Copiar el JAR generado desde la fase de construcción
COPY --from=builder /app/target/creditOnline-0.0.1-SNAPSHOT.jar /app/creditOnline.jar

# Exponer el puerto por donde la aplicación estará disponible
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/creditOnline.jar"]

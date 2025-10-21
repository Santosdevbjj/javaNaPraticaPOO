# 1. Fase de Build (Compilação)
# Usa uma imagem robusta com o JDK 17 para compilar o código
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /app

# Copia e baixa dependências para otimizar o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e constrói o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Fase de Runtime (Execução)
# Usa uma imagem JRE mínima para reduzir o tamanho final do container
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=build /app/target/javaNaPraticaPOO-1.0-SNAPSHOT.jar app.jar

# Expõe a porta que o Spring Boot usa
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

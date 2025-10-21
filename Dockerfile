# 1. Fase de Build (Usa um JDK completo para compilar)
FROM eclipse-temurin:17-jdk-focal AS build
# Define o diretório de trabalho dentro do container
WORKDIR /app
# Copia o pom.xml e faz o download das dependências
COPY pom.xml .
RUN mvn dependency:go-offline
# Copia o código fonte
COPY src ./src
# Compila e empacota a aplicação em um JAR
RUN mvn clean package -DskipTests

# 2. Fase de Runtime (Usa um JRE mínimo para rodar)
FROM eclipse-temurin:17-jre-focal
# Define o diretório de trabalho
WORKDIR /app
# Copia o JAR do estágio de build para o estágio de runtime
COPY --from=build /app/target/*.jar app.jar
# Expõe a porta que o Spring Boot usa
EXPOSE 8080
# Comando para rodar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]

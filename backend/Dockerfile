# Use uma imagem base do Java
FROM openjdk:17-jdk-alpine

# Crie um diretório para a aplicação
WORKDIR /app

# Copie o arquivo JAR gerado pelo Maven
COPY target/*.jar app.jar

# Exponha a porta usada pelo Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
# Usando a imagem base do Ubuntu
FROM ubuntu:latest AS build

# Atualizando a lista de pacotes
RUN apt-get update

# Instalando o JDK 21
RUN apt-get install openjdk-21-jdk -y

# Copiando o código-fonte para a imagem
COPY . .

# Instalando o Maven
RUN apt-get install maven -y

# Construindo o projeto
RUN mvn clean install

# Usando a imagem base do OpenJDK 22 slim para o runtime
FROM openjdk:21-jdk-slim

# Expondo a porta 8080
EXPOSE 8080

# Copiando o JAR gerado na etapa de build para a imagem final
COPY --from=build /target/petshop-0.0.1-SNAPSHOT.jar app.jar

# Definindo o comando de entrada para executar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]


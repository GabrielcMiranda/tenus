# Dockerfile Multi-stage para aplicação Spring Boot

# ESTÁGIO 1: BUILD
# Usa uma imagem Maven com OpenJDK 21 para compilar a aplicação
FROM maven:3.9.9-openjdk-21 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia apenas os arquivos de configuração do Maven primeiro (pom.xml)
# Isso permite cache de dependências quando apenas o código fonte muda
COPY pom.xml .

# Baixa as dependências do Maven (será cacheado se pom.xml não mudou)
RUN mvn dependency:go-offline -B

# Copia todo o código fonte para o container
COPY src ./src

# Compila a aplicação e gera o JAR final
# -DskipTests pula os testes para acelerar o build
RUN mvn clean package -DskipTests

# ESTÁGIO 2: RUNTIME  
# Usa uma imagem mais leve apenas com JRE 21 para executar a aplicação
FROM openjdk:21-jre-slim

# Define o diretório de trabalho para a aplicação
WORKDIR /app

# Cria um usuário não-root para segurança
RUN addgroup --system spring && adduser --system --group spring

# Copia o JAR compilado do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Muda a propriedade do arquivo para o usuário spring
RUN chown spring:spring app.jar

# Muda para o usuário não-root
USER spring:spring

# Expõe a porta 8080 (porta padrão do Spring Boot)
EXPOSE 8080

# Define o comando para executar a aplicação
# Usa flags JVM otimizadas para containers
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
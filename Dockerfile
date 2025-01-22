FROM openjdk:17-jdk-slim

# 작업 디렉토리 생성
WORKDIR /app

# 애플리케이션 JAR 파일 복사
COPY jar/*.jar /app/app.jar

EXPOSE 8080

# Spring 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]
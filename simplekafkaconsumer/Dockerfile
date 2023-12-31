# 베이스 이미지 선택 (Java 실행 환경 포함)
FROM openjdk:17-jdk-alpine

# 애플리케이션 JAR 파일을 컨테이너 내부로 복사하기 위한 경로 설정
ARG JAR_FILE=target/*.jar

# JAR 파일을 컨테이너 내 /app 폴더에 복사
COPY ${JAR_FILE} /app/app.jar

# 컨테이너가 시작될 때 실행할 명령어
ENTRYPOINT ["java","-jar","/app/app.jar"]

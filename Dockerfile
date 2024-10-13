FROM openjdk:21-jdk
LABEL authors="thijnmens"
RUN addgroup -S spring && adduser -S spring -G spring
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","net.vessem.winter.WinterApplication"]
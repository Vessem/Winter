FROM openjdk:21-jdk
LABEL authors="thijnmens"
RUN addgroup -S spring
RUN adduser -S spring -G spring
RUN mkdir -p target/dependency
RUN cd target/dependency
RUN jar -xf ../*.jar
RUN ../../
USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","net.vessem.winter.WinterApplication"]
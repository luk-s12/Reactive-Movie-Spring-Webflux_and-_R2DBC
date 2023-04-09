FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/reactive-movie

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

ARG PROFILE
ARG R2DBC_USERNAME
ARG R2DBC_PASSWORD
ARG SERVER_PORT
ARG	R2DBC_HOST
ARG	R2DBC_DB

RUN ./mvnw -P$PROFILE -Dspring.r2dbc.host=$R2DBC_HOST -Dspring.r2dbc.db=$R2DBC_DB -Dspring.postgres.username=$R2DBC_USERNAME -Dspring.postgres.password=$R2DBC_PASSWORD -Dspring.port=$SERVER_PORT clean install -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

ARG DEPENDENCY=/workspace/reactive-movie/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /reactive-movie/lib
COPY --from=build ${DEPENDENCY}/META-INF /reactive-movie/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /reactive-movie
ENTRYPOINT ["java","-cp","reactive-movie:reactive-movie/lib/*","com.example.demo.ReactiveMovieApplication"]

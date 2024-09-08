FROM postgres
ENV POSTGRES_PASSWORD=example
ENV POSTGRES_USERNAME=postgres
COPY src/main/resources/schema.sql /docker-entrypoint-initdb.d/

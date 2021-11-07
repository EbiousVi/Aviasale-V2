FROM postgres:13.3
LABEL maintainer="EbiousVi" github="https://github.com/EbiousVi"
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=demo
ENV PGDATA=/var/lib/postgresql/data/pgdata
COPY "DOWNLOAD SCRIPT FROM https://edu.postgrespro.ru/demo-small.zip" /docker-entrypoint-initdb.d/
EXPOSE 5432

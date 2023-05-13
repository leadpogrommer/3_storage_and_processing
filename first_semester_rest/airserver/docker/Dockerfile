FROM postgres:15.3-bullseye


ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update && apt-get install -y wget ca-certificates && \
    echo "deb http://apt.postgresql.org/pub/repos/apt/ bullseye-pgdg main" > /etc/apt/sources.list.d/pgdg.list && \
    wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add - && \
    apt-get update

RUN apt-get install -y postgresql-15-pgrouting

COPY sql_init/demo-big-20170815.sql /demo.sql
COPY sql_init/demo.sh /docker-entrypoint-initdb.d/
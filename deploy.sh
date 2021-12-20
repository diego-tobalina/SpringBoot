[ ! -d "portainer-data" ] && mkdir portainer-data
[ ! -d "pgadmin-data" ] && mkdir pgadmin-data
[ ! -d "postgres-data" ] && mkdir postgres-data
docker compose build
docker compose up -d

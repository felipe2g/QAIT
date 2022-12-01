# stackti-app
An QA application to IT companies.

# Docker
docker run --name stackti-db --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123456 -e PGDATA=/var/lib/postgresql/data/pgdata -v /tmp:/var/lib/postgresql/data -p 5432:5432 -d -it postgres:14.1-alpine

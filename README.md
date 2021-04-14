# eduPrj

docker run --name postgresql \ -e POSTGRESQL_USERNAME=postgres \ -e POSTGRESQL_PASSWORD=Passw0rd \ -e POSTGRESQL_DATABASE=eduPrjDb -p 5432:5432 \ bitnami/postgresql:latest
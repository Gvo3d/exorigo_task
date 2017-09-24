docker run --name exorigo -p 5432:5432 -e POSTGRES_USER=exorigo -e POSTGRES_PASSWORD=root -d postgres:9.6
java -jar ROOT.jar
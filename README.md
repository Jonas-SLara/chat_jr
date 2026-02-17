

@Jonas-SLara
## Deploy Local

### Requisitos obrigatorios

- [ ] JDK 21  **ver na adoptium temurin e baixar a lts**
- [ ] Postgres 17 **banco de dados**
- [ ] Mongo 4.4 **banco de dados**
- [ ] visual studio code

### opcionais mas recomendado
- [ ] docker cli e compose  **para virtualizar o postgres e mongo**
- [ ] dbeaver **para visualizar esquemas e tabelas e fazer consultas**
- [ ] maven

### passo a passo na raiz do projeto

1. copiar .env.dev e setar com suas variaveis de ambiente se for usar uma configuração diferente, caso não apenas copie que ja é deixado no
aplication.yml as mesmas configurações padroes

```bash
cp .env.dev .env
```

2. executar docker compose para orquestrar os containers dos bancos de dados no ambiente de desenvolvimento local

```bash
docker compose -f .\docker-compose-dev.yml up -d
```

3. executar o projeto com o wrraper do maven que ja vem instalado, pode ser o proprio maven

```bash
mvn spring-boot:run
```

4. gerar secret HS256

Linux Mac/OS

```bash
openssl rand -base64 32
```

## Deploy para produção com docker (substituir variaveis) na VPS

> criar a rede dos containers

```bash
docker network create chat-net
```
ao se comunicar com outros containers o nome do container substitui o localhost, ip do container

## Deploy do front

> criar o build da imagem

```bash
docker build -t chat-front-image .
```

> execução do container

```bash
docker run -d -p 8000:8000 --name chat-front --network chat-net  chat-front-image
```

### Deploy do container mongo

```bash
docker run -d \
    --name chat-mongodb \
    --network chat-net \
    -p 27017:27017 \
    -e MONGO_INITDB_ROOT_USERNAME=root \
    -e MONGO_INITDB_ROOT_PASSWORD=admin \
    -e MONGO_INITDB_DATABASE=chat \
    mongo:4.4
```

### Deploy do container postgres

```bash
docker run -d \
    --name chat-postgres \
    --network chat-net \
    -p 5432:5432 \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=postgres \
    -e POSTGRES_DB=chat \
    postgres:17
```

### Deploy do container da api

```bash
docker build -t chat-api-image .
```


```bash
docker run -d --name chat-api --network chat-net --env-file .env  chat-api-image
```

### Deploy do nginx

```bash
docker run -d -p 80:80 --name chat-nginx --network chat-net nginx:alpine
```

### alipine o essencial

```bash
docker exec -it chat-nginx sh
apk update          # Atualiza a lista de repositórios
apk add vim         # Instala o editor Vim
apk add curl        # Útil para testar se a API responde de dentros
curl http://chat-api:8080/api/health/
```

bizus
```bash
docker exec chat-nginx cat /etc/nginx/conf.d/default.conf

docker exec chat-nginx nginx -s reload
```




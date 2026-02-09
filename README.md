# nginx_config_example
my nginx config with docker

@Jonas-SLara

## Requisitos obrigatorios

- [ ] JDK 21  **ver na adoptium temurin e baixar a lts**
- [ ] Postgres 17 **banco de dados**
- [ ] Mongo 4.4 **banco de dados**
- [ ] visual studio code

## opcionais mas recomendado
- [ ] docker cli e compose  **para virtualizar o postgres e mongo**
- [ ] dbeaver **para visualizar esquemas e tabelas e fazer consultas**

## passo a passo na raiz do projeto

1. copiar .env.dev e setar com suas variaveis de ambiente se for usar uma configuração diferente, caso não apenas copie que ja é deixado no
aplication.yml as mesmas configurações padroes

```bash
cp .env.dev .env
```

2. executar docker compose para orquestrar os containers dos bancos de dados no ambiente de desenvolvimento local

```bash
docker compose -f .\docker-compose-dev.yml up -d
```

3. executar o projeto com o wrraper do maven que ja vem instalado,
é como se fosse o npm run dev só que no java

```bash
./mvnw spring-boot:run
```

4. gerar secret HS256

Linux Mac/OS

```bash
openssl rand -base64 32
```



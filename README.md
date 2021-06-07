#Levantar o backend da aplicação

1. **Dependências** (Para levantar o projeto você deve ter as aplicações abaixo instaladas)

  - Docker
  - JDK 11 e variáveis configuradas
  - Maven 3.6.3 instalado e configurado
  - Git instalado e configurado

2. **Adicionar instancia do postgres**

```console
$ docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Docker10#" -p 1433:1433 --name sqlserver -h sqlserver -d mcr.microsoft.com/mssql/server:2019-latest
```

Se a sua porta 1433 já estiver em uso, substituir por outra (apenas do lado esquerdo ex: 1434:1433).

3. Entrar no banco de dados e criar um novo banco chamado PUPPYPLACEDB. (Pode usar o DBEAVER pra fazer isso)

3. **Adicionar instância do mongodb**

```console
$ docker run -it --name mongodb -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin mongo
```

4. **Clonar o projeto**

```console
git clone git@github.com:puppyplace/puppyplace-api.git
```
```console
cd back
```
```console
git checkout develop
```

5. **Iniciar a aplicação** (a instancia do postgres e mongodb no docker precisa está de pé)

```console
mvn clean install
```

```console
mvn spring-boot:run
```

# GestRun API

API para gerenciamento de corridas, inscrições, resultados e relatórios. Desenvolvida com Java 21, Spring Boot, PostgreSQL, Flyway, JWT, MapStruct e JasperReports.

## Pré-requisitos

- Java 21 e Maven 3.9+ para execução local; ou Docker Compose.
- PostgreSQL 16+ para execução local.

## Configuração

As credenciais e segredos não são versionados. Use [`.env.example`](.env.example) como referência para os nomes das variáveis.

Para executar localmente no PowerShell, defina as variáveis na sessão:

```powershell
$env:DATABASE_URL = "jdbc:postgresql://localhost:5432/gestrun"
$env:DATABASE_USERNAME = "seu_usuario"
$env:DATABASE_PASSWORD = "sua_senha"
$env:JWT_SECRET = "seu_segredo_base64_com_ao_menos_32_bytes"
$env:CORS_ALLOWED_ORIGINS = "http://localhost:4200"
```

`JWT_EXPIRATION_MS` é opcional e, quando omitida, equivale a uma hora.

## Execução local

Com o PostgreSQL disponível e as variáveis configuradas:

```powershell
mvn spring-boot:run
```

A documentação interativa fica em `http://localhost:8080/docs`.

## Execução pelo IntelliJ IDEA

1. Abra o projeto Maven e configure o SDK para Java 21 em **File > Project Structure > Project**.
2. Em **Run > Edit Configurations**, crie uma configuração do tipo **Spring Boot**.
3. Defina a classe principal como `com.gabrielsmm.gestrun.GestrunApplication`.
4. Em **Environment variables**, informe `APP_PROFILE=dev` e as variáveis de banco, JWT e CORS apresentadas na seção de configuração. No Windows, o campo aceita pares no formato `NOME=valor` separados por `;`.
5. Execute a configuração. A API ficará disponível em `http://localhost:8080`.

Para criar o primeiro administrador, crie uma segunda configuração Spring Boot com a mesma classe principal e as mesmas variáveis de banco, JWT e CORS. Acrescente:

```text
APP_PROFILE=dev,bootstrap-admin
BOOTSTRAP_ADMIN_NOME=Administrador GestRun
BOOTSTRAP_ADMIN_EMAIL=admin@exemplo.com
BOOTSTRAP_ADMIN_SENHA=uma-senha-forte
SPRING_MAIN_WEB_APPLICATION_TYPE=none
```

Execute essa segunda configuração somente para o bootstrap. Ela encerra automaticamente após criar ou conferir o administrador; depois, volte a usar a configuração normal da API.

## Execução com Docker

1. Copie `.env.example` para `.env`.
2. Substitua todos os valores de exemplo por valores reais e seguros.
3. Inicie os serviços:

```bash
docker compose up --build
```

## Primeiro administrador

O banco inicia sem usuários. Crie o primeiro administrador uma única vez pelo perfil `bootstrap-admin`. Esse perfil não abre uma aplicação HTTP: ele cria ou confere o administrador e encerra o processo.

No PowerShell:

```powershell
$env:BOOTSTRAP_ADMIN_NOME = "Administrador GestRun"
$env:BOOTSTRAP_ADMIN_EMAIL = "admin@exemplo.com"
$env:BOOTSTRAP_ADMIN_SENHA = "uma-senha-forte"

mvn spring-boot:run "-Dspring-boot.run.profiles=dev,bootstrap-admin" "-Dspring-boot.run.arguments=--spring.main.web-application-type=none"
```

Em Docker, com o banco já iniciado e as variáveis do `.env` preenchidas:

```bash
docker compose run --rm \
  -e APP_PROFILE=prod,bootstrap-admin \
  -e BOOTSTRAP_ADMIN_NOME="Administrador GestRun" \
  -e BOOTSTRAP_ADMIN_EMAIL="admin@exemplo.com" \
  -e BOOTSTRAP_ADMIN_SENHA="uma-senha-forte" \
  app java -jar app.jar --spring.main.web-application-type=none
```

O comando é idempotente: se o e-mail já for de um administrador, nada é alterado. Se pertencer a outro perfil, ele falha para evitar elevação de privilégio acidental.

Após o bootstrap, use `POST /auth/login`. O cadastro público `POST /auth/registrar` cria somente organizadores; a criação de outros usuários, inclusive administradores, exige um administrador autenticado em `POST /api/usuarios`.

## Segurança operacional

- Em produção, use HTTPS por meio de proxy reverso ou balanceador.
- Não reutilize credenciais entre ambientes e mantenha backups do PostgreSQL.
- Configure `CORS_ALLOWED_ORIGINS` somente com os domínios reais do frontend, separados por vírgula.

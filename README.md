GestRun API
===========

API para gerenciamento de corridas, inscrições e resultados, com autenticação JWT e controle de acessos por perfil.

Sumário
-------
- Tecnologias
- Autenticação
- Swagger
- Como rodar

Tecnologias
-----------
- Java 21
- Spring Boot
- Spring Security com JWT
- Spring Data JPA
- PostgreSQL
- MapStruct
- Springdoc OpenAPI (Swagger)

Autenticação
------------
- A API utiliza JWT para autenticação.
- Usuários podem ter perfis: ADMIN ou ORGANIZADOR.
- Endpoints públicos (como criar inscrição ou consultar resultados) não exigem autenticação.
- Para endpoints protegidos, inclua o header:

  Authorization: Bearer <token>

Exemplo de request de login:

{
"email": "usuario@example.com",
"senha": "senha1234"
}

Exemplo de response:

{
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
"tipo": "Bearer",
"expiraEm": "2025-09-25T18:00:00Z"
}

Swagger
-------
- A documentação interativa está disponível em:
  /swagger-ui/index.html
- Todos os endpoints possuem exemplos de request/response.
- Endpoints públicos não exigem JWT.

Como rodar
----------
1. Clone o repositório:
   git clone https://github.com/gabrielsmm/gestrun-api.git

2. Configure o PostgreSQL e ajuste application.properties

3. Execute a aplicação:
   ./mvnw spring-boot:run

4. Acesse http://localhost:8080/swagger-ui/index.html para testar a API.

# API de Autenticação com Spring Security e JWT

Esta API oferece um sistema de autenticação e registro de usuários usando Spring Security e JWT (JSON Web Tokens).

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Security 6
- JWT (JSON Web Tokens)
- Docker
- Maven

## Funcionalidades

- Registro de novos usuários
- Autenticação de usuários com email e senha
- Geração de tokens JWT para acesso seguro
- Proteção de rotas com autenticação

## Como Executar

### Usando Docker

A maneira mais simples de executar a aplicação é usando Docker:

```bash
# Construir a imagem
docker build -t auth-api .

# Executar o contêiner
docker run -p 8080:8080 auth-api
```

### Sem Docker

Para executar diretamente no seu ambiente:

```bash
./mvnw spring-boot:run
```

## Endpoints

### Registro
```
POST /auth/register
```
Body:
```json
{
  "email": "usuario@exemplo.com",
  "senha": "senha123",
  "nome": "Nome",
  "sobrenome": "Sobrenome",
  "telefone": "123456789"
}
```

### Login
```
POST /auth/login
```
Body:
```json
{
  "email": "usuario@exemplo.com",
  "senha": "senha123"
}
```
Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

## Segurança

A API utiliza Spring Security para autenticação e autorização. Os tokens JWT são necessários para acessar rotas protegidas.

Para acessar rotas protegidas, inclua o token JWT no cabeçalho:
```
Authorization: Bearer {token}
```

## Docker

O projeto utiliza uma abordagem multi-estágio para construir uma imagem Docker otimizada:

1. Primeiro estágio: Compila o código usando Maven
2. Segundo estágio: Cria uma imagem mínima apenas com o JRE e o JAR compilado

A imagem final é baseada no Eclipse Temurin JRE 21 para melhor performance e menor tamanho.

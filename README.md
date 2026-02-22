# 🎬 Movieflix

API REST + Interface Web para cadastro e gerenciamento de filmes, categorias e plataformas de streaming.

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.5.7 |
| Segurança | Spring Security + JWT (java-jwt 4.4.0) |
| Banco de dados | PostgreSQL |
| Migrations | Flyway |
| ORM | Spring Data JPA / Hibernate |
| Interface Web | Thymeleaf + Lucide Icons |
| Documentação | SpringDoc OpenAPI (Swagger UI) |
| Build | Maven |
| Utilitários | Lombok |

---

## Pré-requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL 13+

---

## Configuração do banco de dados

Crie o banco antes de subir a aplicação:

```sql
CREATE DATABASE movieflix;
```

As credenciais padrão configuradas em `application.yaml` são:

```
host:     localhost:5432
database: movieflix
username: postgres
password: postgres
```

Para alterar, edite `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/movieflix
    username: seu_usuario
    password: sua_senha
```

> O Flyway roda as migrations automaticamente na primeira inicialização e cria todas as tabelas.

---

## Rodando o projeto

**Clone o repositório:**

```bash
git clone https://github.com/lunguinhoantonio/cadastro-filmes.git
cd Movieflix
```

**Via Maven Wrapper:**

```bash
./mvnw spring-boot:run
```

**Via Maven instalado:**

```bash
mvn spring-boot:run
```

**Gerando o JAR e executando:**

```bash
./mvnw clean package -DskipTests
java -jar target/Movieflix-0.0.1-SNAPSHOT.jar
```

A aplicação sobe em `http://localhost:8080`.

---

## Interface Web

Acesse pelo navegador após subir a aplicação:

| Página | URL |
|---|---|
| Dashboard | `http://localhost:8080/` |
| Filmes | `http://localhost:8080/movies` |
| Categorias | `http://localhost:8080/categories` |
| Streamings | `http://localhost:8080/streamings` |
| Usuários | `http://localhost:8080/users` |

A interface possui **modo claro e escuro** — basta clicar no ícone ☀/☽ no canto superior direito da navbar. A preferência é salva no navegador.

---

## API REST

A API REST requer autenticação via **JWT**. O fluxo é:

1. Registrar um usuário
2. Fazer login e obter o token
3. Usar o token no header `Authorization` das demais requisições

### Autenticação

#### Registrar usuário
```
POST /movieflix/auth/register
```
```json
{
  "name": "Antonio",
  "email": "antonio@email.com",
  "password": "123456"
}
```

#### Login
```
POST /movieflix/auth/login
```
```json
{
  "email": "antonio@email.com",
  "password": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}
```

Use o token retornado no header de todas as próximas requisições:

```
Authorization: Bearer <token>
```

---

### Filmes — `/movieflix/movie`

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/movieflix/movie` | Lista todos os filmes |
| GET | `/movieflix/movie/{id}` | Busca filme por ID |
| GET | `/movieflix/movie/search?categoryId={id}` | Filtra filmes por categoria |
| POST | `/movieflix/movie` | Cadastra novo filme |
| PUT | `/movieflix/movie/{id}` | Atualiza filme existente |
| DELETE | `/movieflix/movie/{id}` | Remove filme |

**Exemplo de body (POST/PUT):**
```json
{
  "title": "Oppenheimer",
  "description": "A história do criador da bomba atômica.",
  "releaseDate": "21/07/2023",
  "rating": 8.9,
  "categories": [1, 2],
  "streamings": [1]
}
```

> A data deve estar no formato `dd/MM/yyyy`.

---

### Categorias — `/movieflix/category`

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/movieflix/category` | Lista todas as categorias |
| GET | `/movieflix/category/{id}` | Busca categoria por ID |
| POST | `/movieflix/category` | Cadastra nova categoria |
| DELETE | `/movieflix/category/{id}` | Remove categoria |

**Exemplo de body (POST):**
```json
{
  "name": "Drama"
}
```

---

### Streamings — `/movieflix/streaming`

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/movieflix/streaming` | Lista todos os streamings |
| GET | `/movieflix/streaming/{id}` | Busca streaming por ID |
| POST | `/movieflix/streaming` | Cadastra novo streaming |
| DELETE | `/movieflix/streaming/{id}` | Remove streaming |

**Exemplo de body (POST):**
```json
{
  "name": "Netflix"
}
```

---

## Swagger UI

A documentação interativa da API está disponível em:

```
http://localhost:8080/swagger/index.html
```

O JSON do OpenAPI pode ser acessado em:

```
http://localhost:8080/api/api-docs
```

---

## Estrutura do projeto

```
src/main/java/dev/lunguinhoantonio/Movieflix/
├── config/
│   ├── SecurityConfig.java       # Configuração do Spring Security e JWT
│   ├── SecurityFilter.java       # Filtro de autenticação JWT por requisição
│   ├── TokenService.java         # Geração e validação de tokens JWT
│   └── JWTUserData.java
├── controller/
│   ├── AuthController.java       # POST /register e /login
│   ├── MovieController.java      # REST API de filmes
│   ├── CategoryController.java   # REST API de categorias
│   ├── StreamingController.java  # REST API de streamings
│   └── ui/
│       ├── HomeController.java         # GET / (dashboard)
│       ├── MovieViewController.java    # Interface web de filmes
│       ├── CategoryViewController.java # Interface web de categorias
│       ├── StreamingViewController.java
│       ├── UserViewController.java
│       └── GlobalExceptionHandler.java
├── entity/
│   ├── Movie.java
│   ├── Category.java
│   ├── Streaming.java
│   └── User.java
├── service/
│   ├── MovieService.java
│   ├── CategoryService.java
│   ├── StreamingService.java
│   ├── UserService.java
│   └── AuthService.java
├── repository/         # Interfaces JPA
├── mapper/             # Conversão Entity ↔ Request/Response
└── controller/
    ├── request/        # DTOs de entrada
    └── response/       # DTOs de saída

src/main/resources/
├── templates/          # Templates Thymeleaf
│   ├── fragments/layout.html
│   ├── index.html
│   ├── movies/
│   ├── categories/
│   ├── streamings/
│   └── users/
├── static/
│   ├── css/main.css
│   └── js/main.js
├── db/migration/       # Scripts Flyway (V1 a V8)
└── application.yaml
```

---

## Migrations (Flyway)

As migrations são executadas automaticamente na ordem:

| Versão | Descrição |
|---|---|
| V1 | Criação da tabela `category` |
| V2–V4 | Criação da tabela `streaming` (com ajustes) |
| V5 | Criação da tabela `movie` |
| V6 | Tabela de junção `movie_category` |
| V7 | Tabela de junção `movie_streaming` |
| V8 | Criação da tabela `users` |

---

## Observações

- A **interface web** (`/movies`, `/categories`, `/streamings`, `/users`) é **aberta** — não exige autenticação.
- A **API REST** (`/movieflix/**`) exige o header `Authorization: Bearer <token>` em todas as rotas, exceto `/movieflix/auth/register` e `/movieflix/auth/login`.
- O token JWT expira conforme configurado em `TokenService.java`.
- A senha do secret JWT está hardcoded em `application.yaml` — em produção, substitua por uma variável de ambiente.

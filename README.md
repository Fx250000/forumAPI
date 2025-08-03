# 🎯 Forum API - Challenge Alura

Uma API REST completa para sistema de fórum desenvolvida com **Spring Boot 3**, **JWT Authentication** e **Spring Security 6**.

Esta API permite que usuários registrados criem, editem, listem e removam tópicos de discussão. Cada tópico pertence a um curso e apenas o autor pode modificar seus próprios tópicos, garantindo segurança e integridade dos dados.

## ✨ Principais Funcionalidades

- 🔐 **Autenticação JWT** - Sistema seguro de login e registro
- 📝 **CRUD Completo de Tópicos** - Criar, ler, atualizar e deletar
- 🏫 **Organização por Cursos** - Tópicos categorizados por curso
- 👤 **Controle de Autoria** - Apenas autores podem modificar seus tópicos
- 📊 **Paginação e Filtros** - Listagem eficiente com busca avançada
- 📈 **Estatísticas** - Métricas sobre tópicos, usuários e cursos
- 🛡️ **Segurança Robusta** - Validações e tratamento de exceções
- 🔍 **Busca Avançada** - Buscar tópicos por título, mensagem e curso
- 👥 **Gestão de Usuários** - Sistema completo de usuários
- 📋 **Meus Tópicos** - Visualizar apenas tópicos próprios
- 🎓 **Tópicos por Curso** - Filtrar tópicos por curso específico
- 📖 **Documentação Swagger** - Interface interativa para testar a API

## 🛠️ Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.4** - Framework principal
- **Spring Security 6** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **JWT (JSON Web Token)** - Autenticação stateless
- **H2 Database** - Banco de dados em memória
- **Maven** - Gerenciador de dependências
- **Bean Validation** - Validação de dados
- **SpringDoc OpenAPI 3** - Documentação automática da API
- **Swagger UI** - Interface interativa para testes da API

## 📦 Principais Dependências

```xml
<!-- JWT Authentication -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>

<!-- Spring Boot Starters -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Swagger/OpenAPI Documentation -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```

## 📁 Estrutura do Projeto

```
src/main/java/br/com/alura/forumapi/
├── auth/
│   ├── controller/     # Endpoints de autenticação
│   ├── dto/           # DTOs de login/registro
│   ├── filter/        # Filtro JWT
│   └── service/       # Serviços de autenticação
├── config/
│   └── SecurityConfig.java  # Configuração de segurança
├── course/
│   ├── entity/        # Entidade Course
│   ├── repository/    # Repositório de cursos
│   └── service/       # Lógica de negócio de cursos
├── topic/
│   ├── controller/    # Endpoints de tópicos
│   ├── dto/          # DTOs de tópicos
│   ├── entity/       # Entidade Topic
│   ├── repository/   # Repositório de tópicos
│   └── service/      # Lógica de negócio de tópicos
├── user/
│   ├── entity/       # Entidade User
│   ├── repository/   # Repositório de usuários
│   └── service/      # Lógica de negócio de usuários
└── stats/
    └── controller/   # Endpoints de estatísticas
```

## 🚀 Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- IDE de sua preferência (IntelliJ IDEA recomendado)

## ⚡ Instalação e Execução

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Fx250000/forumAPI.git
   cd forumAPI
   ```

2. **Compile o projeto**
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

4. **Acesse a aplicação**
   - **API**: [http://localhost:8080](http://localhost:8080)
   - **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
   - **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
     - JDBC URL: `jdbc:h2:mem:forumdb`
     - Username: `sa`
     - Password: (vazio)

## 🔐 Autenticação

A API utiliza JWT (JSON Web Token) para autenticação. Após o login, inclua o token no header de todas as requisições protegidas:

```
Authorization: Bearer seu_token_jwt_aqui
```

## 📖 Documentação Interativa

### Swagger UI
A API possui documentação interativa completa através do Swagger UI, onde você pode:

- 🔍 **Visualizar todos os endpoints** disponíveis
- 📝 **Testar requisições** diretamente na interface
- 📋 **Ver modelos de dados** e exemplos
- 🔐 **Autenticar com JWT** diretamente na interface
- 📄 **Exportar especificação OpenAPI** em JSON/YAML

**Acesse**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Como usar o Swagger UI:
1. Acesse o Swagger UI no link acima
2. Faça login através do endpoint `/auth/login`
3. Copie o token JWT retornado
4. Clique no botão **"Authorize"** no topo da página
5. Cole o token no formato: `Bearer {seu_token_aqui}`
6. Agora você pode testar todos os endpoints protegidos!

### OpenAPI Specification
- **JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **YAML**: [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

## 📚 Documentação da API

### 🔑 Autenticação

#### Registrar Usuário
```http
POST /auth/register
Content-Type: application/json

{
    "username": "joao123",
    "email": "joao@exemplo.com",
    "password": "senha123456"
}
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
    "username": "joao123",
    "password": "senha123456"
}
```

**Resposta:**
```json
{
    "success": true,
    "message": "Login realizado com sucesso",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "type": "Bearer",
        "username": "joao123",
        "email": "joao@exemplo.com"
    }
}
```

### 📝 Tópicos

#### Listar Tópicos com Filtros e Paginação
```http
GET /topics?page=0&size=10&sortBy=createdAt&sortDir=desc&courseName=Java&search=Spring
Authorization: Bearer {token}
```

**Parâmetros opcionais:**
- `page`: Número da página (padrão: 0)
- `size`: Itens por página (padrão: 10)
- `sortBy`: Campo para ordenação (padrão: createdAt)
- `sortDir`: Direção da ordenação - asc/desc (padrão: desc)
- `courseName`: Filtrar por curso
- `search`: Buscar no título e mensagem

#### Criar Tópico
```http
POST /topics
Authorization: Bearer {token}
Content-Type: application/json

{
    "title": "Dúvida sobre Spring Boot",
    "message": "Como configurar JWT corretamente?",
    "courseName": "Java"
}
```

#### Obter Tópico por ID
```http
GET /topics/{id}
Authorization: Bearer {token}
```

#### Atualizar Tópico
```http
PUT /topics/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
    "title": "Título atualizado",
    "message": "Mensagem atualizada",
    "courseName": "Java"
}
```

#### Deletar Tópico
```http
DELETE /topics/{id}
Authorization: Bearer {token}
```

#### 🆕 Listar Meus Tópicos
```http
GET /topics/my-topics
Authorization: Bearer {token}
```

#### 🆕 Listar Tópicos por Curso
```http
GET /topics/course/{courseName}
Authorization: Bearer {token}
```

### 📊 Estatísticas

#### 🆕 Estatísticas Gerais
```http
GET /stats
Authorization: Bearer {token}
```

#### 🆕 Estatísticas por Curso
```http
GET /stats/course/{courseName}
Authorization: Bearer {token}
```

## 📋 Modelos de Dados

### Usuário
```json
{
    "id": 1,
    "username": "joao123",
    "email": "joao@exemplo.com",
    "createdAt": "2025-07-28 21:00:00"
}
```

### Tópico
```json
{
    "id": 1,
    "title": "Dúvida sobre Spring Boot",
    "message": "Como configurar JWT corretamente?",
    "authorUsername": "joao123",
    "courseName": "Java",
    "createdAt": "2025-07-28 21:00:00",
    "updatedAt": "2025-07-28 21:00:00"
}
```

### Curso
```json
{
    "id": 1,
    "name": "Java",
    "description": "Curso de Java"
}
```

## 🔒 Recursos de Segurança

- ✅ Apenas usuários registrados podem acessar a API
- ✅ Token JWT expira em 24 horas
- ✅ Senhas são criptografadas com BCrypt
- ✅ Apenas usuários autenticados podem criar tópicos
- ✅ Apenas o autor pode atualizar/deletar seus tópicos
- ✅ Cursos são criados automaticamente se não existirem

## ✅ Validações

- ✅ Todos os campos obrigatórios são validados
- ✅ **Username**: 3-50 caracteres, único
- ✅ **Email**: formato válido, único
- ✅ **Password**: 6-100 caracteres
- ✅ **Título do tópico**: 5-200 caracteres
- ✅ **Mensagem**: 10-2000 caracteres
- ✅ **Nome do curso**: 2-100 caracteres

## 🧪 Como Testar

### Opção 1: Usando Swagger UI (Recomendado)
1. Acesse [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
2. Registre um usuário usando o endpoint `POST /auth/register`
3. Faça login usando `POST /auth/login` e copie o token
4. Clique em **"Authorize"** e cole o token no formato `Bearer {token}`
5. Teste todos os endpoints diretamente na interface!

### Opção 2: Usando ferramentas externas (Postman, cURL, etc.)
1. Registre um usuário em `/auth/register`
2. Faça login em `/auth/login` e copie o token
3. Use o token no header `Authorization: Bearer {token}`
4. Teste todos os endpoints conforme documentação acima

### Fluxo de Teste Completo
1. **Registrar** → **Login** → **Criar Tópico** → **Listar Tópicos** → **Atualizar Tópico** → **Deletar Tópico**

### Testes de Segurança
- Tentar acessar sem token (deve retornar 401)
- Tentar editar tópico de outro usuário (deve retornar 403)
- Dados inválidos (deve retornar 400 com detalhes)

## ⚙️ Configuração

```properties
# Banco H2
spring.datasource.url=jdbc:h2:mem:forumdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Console H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Swagger/OpenAPI
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method

# JWT
jwt.secret=minhachavesecretasuperseguraparajwtquedevetersomaiores256bits
jwt.expiration=86400000
```

## ❌ Tratamento de Erros

A API retorna erros padronizados no formato:

```json
{
    "status": 400,
    "error": "Dados inválidos",
    "message": "Verifique os campos obrigatórios",
    "details": ["Username deve ter entre 3 e 50 caracteres"],
    "timestamp": "2025-07-28 21:00:00"
}
```

### Códigos de Status HTTP
- **200** - Sucesso
- **400** - Dados inválidos
- **401** - Não autenticado
- **403** - Sem permissão
- **404** - Não encontrado
- **500** - Erro interno do servidor

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

**Douglas Holz**
- GitHub: [https://github.com/Fx250000](https://github.com/Fx250000)
- Email: [d.holz@live.com](mailto:d.holz@live.com)

## 🙏 Agradecimentos

- [Alura](https://alura.com.br) pelo desafio e conhecimento
- [Spring Boot](https://spring.io/projects/spring-boot) pela excelente documentação
- [JWT.io](https://jwt.io) pelas ferramentas de debug JWT

---

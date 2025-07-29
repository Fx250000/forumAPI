text
# 🏛️ Forum API - Alura Challenge

Uma API REST completa para sistema de fórum desenvolvida com **Spring Boot 3**, **JWT Authentication** e **Spring Security 6**.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)
![Maven](https://img.shields.io/badge/Maven-4.0.0-blue)
![H2 Database](https://img.shields.io/badge/Database-H2-lightblue)
![JWT](https://img.shields.io/badge/Auth-JWT-red)

## 📋 Sobre o Projeto

Esta API permite que usuários registrados criem, editem, listem e removam tópicos de discussão. Cada tópico pertence a um curso e apenas o autor pode modificar seus próprios tópicos, garantindo segurança e integridade dos dados.

### ✨ Funcionalidades Principais

- 🔐 **Autenticação JWT** - Sistema seguro de login e registro
- 📝 **CRUD Completo de Tópicos** - Criar, ler, atualizar e deletar
- 🏫 **Organização por Cursos** - Tópicos categorizados por curso
- 👤 **Controle de Autoria** - Apenas autores podem modificar seus tópicos
- 📊 **Paginação e Filtros** - Listagem eficiente com busca avançada
- 📈 **Estatísticas** - Métricas sobre tópicos, usuários e cursos
- 🛡️ **Segurança Robusta** - Validações e tratamento de exceções

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.4** - Framework principal
- **Spring Security 6** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **JWT (JSON Web Token)** - Autenticação stateless
- **H2 Database** - Banco de dados em memória
- **Maven** - Gerenciador de dependências
- **Bean Validation** - Validação de dados

### Dependências Principais
<!-- JWT Authentication --> <dependency> <groupId>io.jsonwebtoken</groupId> <artifactId>jjwt-api</artifactId> <version>0.11.5</version> </dependency> <!-- Spring Boot Starters --> <dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-web</artifactId> </dependency> <dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-security</artifactId> </dependency> <dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-data-jpa</artifactId> </dependency> ```
🏗️ Arquitetura do Projeto
text
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
⚙️ Como Executar o Projeto
Pré-requisitos
Java 17 ou superior

Maven 3.8+

IDE de sua preferência (IntelliJ IDEA recomendado)

Passos para execução
Clone o repositório

text
git clone https://github.com/seu-usuario/forum-api.git
cd forum-api
Compile o projeto

text
mvn clean install
Execute a aplicação

text
mvn spring-boot:run
Acesse a aplicação

API: http://localhost:8080

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:forumdb

Username: sa

Password: (vazio)

📚 Documentação da API
🔐 Autenticação
A API utiliza JWT (JSON Web Token) para autenticação. Após o login, inclua o token no header de todas as requisições protegidas:

text
Authorization: Bearer seu_token_jwt_aqui
Registro de Usuário
text
POST /auth/register
Content-Type: application/json

{
  "username": "joao123",
  "email": "joao@exemplo.com",
  "password": "senha123456"
}
Login
text
POST /auth/login
Content-Type: application/json

{
  "username": "joao123",
  "password": "senha123456"
}
Resposta:

text
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
📝 Endpoints de Tópicos
Listar Tópicos (com paginação e filtros)
text
GET /topics?page=0&size=10&sortBy=createdAt&sortDir=desc&courseName=Java&search=Spring
Authorization: Bearer {token}
Parâmetros opcionais:

page: Número da página (padrão: 0)

size: Itens por página (padrão: 10)

sortBy: Campo para ordenação (padrão: createdAt)

sortDir: Direção da ordenação - asc/desc (padrão: desc)

courseName: Filtrar por curso

search: Buscar no título e mensagem

Criar Tópico
text
POST /topics
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Dúvida sobre Spring Boot",
  "message": "Como configurar JWT corretamente?",
  "courseName": "Java"
}
Buscar Tópico por ID
text
GET /topics/{id}
Authorization: Bearer {token}
Atualizar Tópico (apenas o autor)
text
PUT /topics/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Título atualizado",
  "message": "Mensagem atualizada",
  "courseName": "Java"
}
Deletar Tópico (apenas o autor)
text
DELETE /topics/{id}
Authorization: Bearer {token}
Meus Tópicos
text
GET /topics/my-topics
Authorization: Bearer {token}
Tópicos por Curso
text
GET /topics/course/{courseName}
Authorization: Bearer {token}
📊 Endpoints de Estatísticas
Estatísticas Gerais
text
GET /stats
Authorization: Bearer {token}
Estatísticas por Curso
text
GET /stats/course/{courseName}
Authorization: Bearer {token}
🗄️ Modelo de Dados
User
text
{
  "id": 1,
  "username": "joao123",
  "email": "joao@exemplo.com",
  "createdAt": "2025-07-28 21:00:00"
}
Topic
text
{
  "id": 1,
  "title": "Dúvida sobre Spring Boot",
  "message": "Como configurar JWT corretamente?",
  "authorUsername": "joao123",
  "courseName": "Java",
  "createdAt": "2025-07-28 21:00:00",
  "updatedAt": "2025-07-28 21:00:00"
}
Course
text
{
  "id": 1,
  "name": "Java",
  "description": "Curso de Java"
}
🛡️ Regras de Negócio
Autenticação
✅ Apenas usuários registrados podem acessar a API

✅ Token JWT expira em 24 horas

✅ Senhas são criptografadas com BCrypt

Tópicos
✅ Apenas usuários autenticados podem criar tópicos

✅ Apenas o autor pode atualizar/deletar seus tópicos

✅ Cursos são criados automaticamente se não existirem

✅ Todos os campos obrigatórios são validados

Validações
✅ Username: 3-50 caracteres, único

✅ Email: formato válido, único

✅ Password: 6-100 caracteres

✅ Título do tópico: 5-200 caracteres

✅ Mensagem: 10-2000 caracteres

✅ Nome do curso: 2-100 caracteres

🧪 Testando a API
Com Postman/Insomnia
Registre um usuário em /auth/register

Faça login em /auth/login e copie o token

Use o token no header Authorization: Bearer {token}

Teste todos os endpoints conforme documentação acima

Exemplos de Teste
Teste básico de fluxo:

Registrar → Login → Criar Tópico → Listar Tópicos → Atualizar Tópico → Deletar Tópico

Teste de segurança:

Tentar acessar sem token (deve retornar 401)

Tentar editar tópico de outro usuário (deve retornar 403)

Dados inválidos (deve retornar 400 com detalhes)

🔧 Configurações
Banco de Dados H2
text
spring.datasource.url=jdbc:h2:mem:forumdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
JWT
text
jwt.secret=minhachavesecretasuperseguraparajwtquedevetersomaiores256bits
jwt.expiration=86400000
🚨 Tratamento de Erros
A API retorna erros padronizados no formato:

text
{
  "status": 400,
  "error": "Dados inválidos",
  "message": "Verifique os campos obrigatórios",
  "details": ["Username deve ter entre 3 e 50 caracteres"],
  "timestamp": "2025-07-28 21:00:00"
}
Códigos de Status
200 - Sucesso

400 - Dados inválidos

401 - Não autenticado

403 - Sem permissão

404 - Não encontrado

500 - Erro interno do servidor

🤝 Contribuindo
Fork o projeto

Crie uma branch para sua feature (git checkout -b feature/AmazingFeature)

Commit suas mudanças (git commit -m 'Add some AmazingFeature')

Push para a branch (git push origin feature/AmazingFeature)

Abra um Pull Request

📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

👨‍💻 Autor
Seu Nome

GitHub: @seu-usuario

LinkedIn: Seu LinkedIn

Email: seu.email@exemplo.com

🙏 Agradecimentos
Alura pelo desafio e conhecimento

Spring Boot pela excelente documentação

JWT.io pelas ferramentas de debug JWT

⭐ Se este projeto te ajudou, não esqueça de dar uma estrela!

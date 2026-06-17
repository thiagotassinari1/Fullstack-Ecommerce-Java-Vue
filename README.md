# 🛒 E-Commerce Full Stack

![Java](https://img.shields.io/badge/Java-17-orange?logo=java) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green?logo=springboot) ![Vue.js](https://img.shields.io/badge/Vue.js-3-4FC08D?logo=vue.js) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-336791?logo=postgresql)

Um sistema completo de E-commerce desenvolvido com uma arquitetura separada entre Backend (API REST) e Frontend (SPA). O projeto gerencia o cadastro e a listagem de categorias e produtos, implementando operações de CRUD completas e estruturadas.

---

## 📋 Características

- ✅ API REST totalmente documentada com Swagger
- ✅ CRUD completo de categorias e produtos
- ✅ Interface responsiva e moderna
- ✅ Integração frontend-backend com Axios
- ✅ Banco de dados relacional PostgreSQL
- ✅ Validação de dados no backend
- ✅ Projeto estruturado em monorepo (subdividido em pastas para backend e frontend)

---

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 17** — Linguagem de programação
- **Spring Boot 3.5** — Web, Data JPA, Validation
- **PostgreSQL** — Banco de dados relacional
- **Lombok** — Redução de boilerplate
- **Springdoc OpenAPI** — Documentação interativa com Swagger
- **Maven** — Gerenciamento de dependências

### Frontend
- **Vue.js 3** — Framework JavaScript (Composition API)
- **Vite** — Build tool de alta performance
- **Axios** — Cliente HTTP para consumo da API REST

---

## 📁 Estrutura do Repositório

Projeto em estrutura de **monorepo** com as aplicações na mesma base de código:

```
.
├── ecommerce/                    # Backend (API REST)
│   ├── src/main/java/
│   ├── src/main/resources/
│   ├── pom.xml
│   └── ...
├── ecommerce-frontend/           # Frontend (SPA)
│   ├── src/
│   ├── package.json
│   └── ...
└── README.md
```

---

## ⚙️ Como executar o projeto localmente

### Pré-requisitos

- ✓ **Java 17+** instalado
- ✓ **Node.js v18+** instalado
- ✓ **PostgreSQL 14+** rodando localmente (porta padrão: `5432`)
- ✓ **Git** instalado

### 0️⃣ Clonando o Repositório

1. Clone o repositório do projeto:
   ```bash
   git clone https://github.com/thiagotassinari1/Fullstack-Ecommerce-Java-Vue.git
   ```

2. Acesse a pasta do projeto:
   ```bash
   cd Fullstack-Ecommerce-Java-Vue
   ```

### 1️⃣ Configurando e Rodando o Backend

1. Navegue até a pasta do backend:
   ```bash
   cd ecommerce
   ```

2. Configure as credenciais do banco de dados:
   - Vá até `src/main/resources/`
   - Crie um arquivo chamado `application.properties` (ignorado por Git por segurança)
   - Copie o conteúdo de `application.properties.example` para o novo arquivo
   - Substitua `SEU_USUARIO_AQUI` e `SUA_SENHA_AQUI` pelas credenciais reais

3. Instale as dependências e inicie o servidor:
   ```bash
   ./mvnw spring-boot:run
   ```

4. A API estará acessível em:
   - **Base URL:** http://localhost:8080
   - **Swagger UI:** http://localhost:8080/swagger-ui.html

### 2️⃣ Configurando e Rodando o Frontend

1. Abra uma nova aba do terminal e navegue até o frontend:
   ```bash
   cd ecommerce-frontend
   ```

2. Instale as dependências:
   ```bash
   npm install
   ```

3. Inicie o servidor de desenvolvimento:
   ```bash
   npm run dev
   ```

4. O frontend estará acessível no navegador em: http://localhost:5173

---

## 🔗 URLs de Acesso

| Componente | URL |
|-----------|-----|
| **Frontend** | http://localhost:5173 |
| **Backend** | http://localhost:8080 |
| **API Docs** | http://localhost:8080/swagger-ui.html |

---

## 📚 Documentação da API

Acesse o **Swagger UI** para testar todos os endpoints interativamente:

```
http://localhost:8080/swagger-ui.html
```

---

## 👨‍💻 Autor

**Thiago da Silva Tassinari** — Desenvolvedor Full Stack

---

## 📄 Licença

Projeto desenvolvido para fins de estudo e demonstração em portfólio.

---
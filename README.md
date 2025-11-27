# projeto-final-SPOLPG2-backend
# 🎵 Vinyl Collection Manager (VCM)

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/flyway-%23CC0200.svg?style=for-the-badge&logo=flyway&logoColor=white)

## 📖 Sobre o Projeto

Este projeto é o Trabalho Final da disciplina de **Linguagem de Programação 2** do curso de **Sistemas de Informação do IFSP** (Instituto Federal de São Paulo).

Trata-se de uma plataforma Full Stack para gerenciamento de coleções de discos de vinil, inspirada em grandes bases de dados como **Discogs** e **RateYourMusic**. O sistema resolve o problema de catalogação de mídias físicas, permitindo diferenciar a obra musical abstrata (Master Release) de suas diversas prensagens físicas (Releases), além de oferecer funcionalidades de rede social com avaliações e reviews.

## 🚀 Funcionalidades Principais

* **Autenticação Segura:** Login e Registro com **Spring Security**, **JWT (JSON Web Token)** e **RBAC (Role Based Access Control)**.
* **Catálogo Discográfico:**
    * Cadastro de Artistas, Gravadoras (Labels) e Países.
    * Diferenciação entre **Obra (Master)** e **Edição (Release)**.
    * Suporte a lançamentos globais (múltiplos países e gravadoras).
* **Gestão de Coleção:** Usuários podem adicionar itens à coleção pessoal, indicando estado de conservação (Mídia/Capa) e notas privadas.
* **Interação Social:** Sistema de avaliações e comentários nos álbuns.
* **Versionamento de Banco:** Migrations automatizadas e seguras com **Flyway**.

## 🛠️ Tecnologias Utilizadas

### Back-end
* **Java 21**
* **Spring Boot 3** (Web, Data JPA, Validation, Security)
* **PostgreSQL** (Banco de Dados Relacional)
* **Flyway** (Gerenciamento de Migrations)
* **Lombok** (Redução de boilerplate)
* **Bean Validation** (Regras de integridade de dados)

### Front-end (Em desenvolvimento)
* **Vue.js 3** (Composition API)
* **Vite**
* **Axios** (Integração HTTP)
* **Pinia** (Gerenciamento de Estado)

## ⚙️ Como Executar

### Pré-requisitos
* Java 17+
* Docker (para o banco de dados) ou PostgreSQL instalado localmente
* Gradle (ou use o wrapper `./gradlew` incluso)

### Passo a Passo

1.  **Clone o repositório**
    ```bash
    git clone [https://github.com/rafaeltpaulino/projeto-final-SPOLPG2-backend](https://github.com/rafaeltpaulino/projeto-final-SPOLPG2-backend)
    cd projeto-final-SPOLPG2-backend
    ```

2.  **Configuração do Banco de Dados**
    Certifique-se de ter um banco PostgreSQL criado (ex: `vinyl_db`). Atualize o arquivo `src/main/resources/application.properties` com suas credenciais:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/vinyl_db
    spring.datasource.username=postgres
    spring.datasource.password=sua_senha
    ```

3.  **Execute a Aplicação**
    O Flyway irá criar as tabelas automaticamente na inicialização.
    ```bash
    cd backend
    ./gradlew bootRun
    ```

4.  **Acesse**
    A API estará rodando em `http://localhost:8080`.
    * Documentação Swagger: `http://localhost:8080/swagger-ui/index.html`

## 🤝 Autores

* **Amanda Jen** - [GitHub](https://github.com/AmandaJen)
* **Kethelyn Alves** - [GitHub](https://github.com/KethelynAlves)
* **Rafael Paulino** - [GitHub](https://github.com/rafaeltpaulino)
* **Vinicius Tiago** - [GitHub](https://github.com/EnterName23)
---
*Projeto desenvolvido para a disciplina de Linguagem de Programação 2 - IFSP.*

# Vinicius Vianna API

## 🎯Objetivo
Este projeto tem por fim implementar um sistema de catalogação de objetos astronômicos/celestes com arquitetura proposta de multi-módulos, separação de responsabilidades, integração externa e API REST documentada.

## Feature 04 - Autorização e Autenticação

### 🔐 Sistema de Segurança Implementado
**Spring Security com autenticação básica HTTP:**
- Configuração completa de segurança em `SecurityConfig`
- Suporte a múltiplos níveis de acesso (ADMIN, USER)
- Criptografia de senhas com BCryptPasswordEncoder
- Proteção de endpoints baseada em roles
- Pré Autorização Somente no `PlanetaController`

### 👥 Usuários e Permissões
**Usuários configurados:**
- **admin/admin**: Acesso total (CREATE, READ, UPDATE, DELETE)
- **user/user**: Acesso somente leitura (READ only)

**Endpoints liberados sem autenticação:**
- `/h2-console/**` - Console do banco H2
- `/swagger-ui/**` - Interface do Swagger
- `/v3/api-docs/**` - Documentação da API
- `/GET /api/**` - Métodos GET

**Endpoints protegidos:**
- `POST/PUT/PATCH /api/**` - Requer role ADMIN e USER
- `DELETE /api/**` - Requer role ADMIN

---

## Feature 03 - Modularização e DTOs
Foi feita uma refatorção e modularização de dois projetos:
1. [stellar-index](https://github.com/lanzgit/stellar-index): implementado na primeira disciplina do curso
2. [vinicius-vianna-api](https://github.com/lanzgit/Vinicius-vianna-api): implementado na segunda disciplina até a [`feature 02`](#feature-02---integração-com-api-pública-externa) com sua estrutura inicial

### 🏗️ Estrutura de Projeto Modular
**Organização otimizada:**
```
vinicius-parent/                # POM pai (não executável)
├── pom.xml                     # Gerenciamento de dependências
├── external-api/               # Integração API externa
├── common-domain/              # Entidades compartilhadas  
└── main-app/                   # ← Aplicação executável
    ├── src/main/java/.../config/
    │   └── SecurityConfig.java # Configuração de segurança
    └── application.properties  # Configurações da aplicação
```
  
### Principais Funcionalidades Implementadas
**1. Modelo de Domínio (common-domain)**
- Classe abstrata Astro como superclasse
- Entidades: Planeta, Lua, Estrela, Asteroide
- Enums: ConstelacaoEnum, LuminosidadeEnum
- Relacionamentos JPA: Planeta 1:N Lua
- Sistema de validação com annotations
  
**2. API Externa (external-api)**
- Cliente Feign NasaSbdbClient para integração com NASA API
- NasaCorpoCelesteService para buscar dados de asteroides
- DTOs para mapeamento de dados externos
- Tratamento de NEOs (Near-Earth Objects)
  
**3. Aplicação Principal (main-app)**
- Controllers REST:
  - PlanetaController, LuaController, EstrelaController, AsteroideController
  - Endpoints CRUD completos com validação
  - Documentação Swagger/OpenAPI
- Services:
  - Implementação da interface `CrudService<T, ID>` 
  - ValidacaoService para regras de negócio
  - Integração opcional com API externa
- Repositories JPA:
  - Queries customizadas com `@Query` 
  - Métodos de busca específicos (ex: luas por distância orbital)
- DTOs de Request/Response **(Somente de `Planeta`)**:
  - PlanetaRequestDTO, PlanetaResponseDTO
  - Conversores estáticos para mapeamento

**4. Sistema de Carga de Dados**
- ApplicationRunners com @Order para carga sequencial:
  - PlanetaLoader (Order 1)
  - LuaLoader (Order 2)
  - EstrelaLoader (Order 3)
  - AsteroideLoader (Order 4)
- Leitura de arquivos .txt com dados iniciais
- Tratamento de exceções durante carga

### Principais Correções Realizadas
**1. Resolução de Dependências**
- Configuração do Maven compiler com flag -parameters
- Correção de conflitos entre módulos
- Configuração adequada do Spring Boot

**2. Correção de LazyInitializationException**
- Implementação de queries com JOIN FETCH no PlanetaRepository
- Métodos findAllWithLuas() e findByIdWithLuas()
- Tratamento adequado de relacionamentos lazy
  
**3. Correção de PathVariable**
- Adição explícita de nomes em @PathVariable("id")
- Correção em todos os controllers
  
**4. Integração com API Externa**
- Tornada opcional com @Autowired(required = false)
- Tratamento de erros quando serviço externo indisponível
- Fallback para dados locais
  
### Configurações
**Database H2**
- Configuração in-memory para desenvolvimento
- Console H2 habilitado
- JPA com DDL auto-update
 
**Swagger/OpenAPI**
- Documentação automática da API
- Configuração personalizada em SwaggerConfig

**Spring Cloud OpenFeign**
- Cliente HTTP declarativo
- Integração com NASA Small-Body Database

### Estrutura de Arquivos de Dados
- **planeta.txt**: Dados iniciais de planetas
- **lua.txt**: Dados de satélites naturais
- **estrela.txt**: Dados de estrelas com constelações
- **asteroide.txt**: Dados de asteroides e NEOs
  

## Feature 02 - Integração com API pública externa
### 🌌 Integração com API da NASA
**Implementação de client para NASA Small-Body Database API:**
- Cliente Feign configurado com tratamento de erros
- Modelo de domínio `CorpoCeleste` simplificado
- Mapeamento automático de dados da API externa
- Endpoints para consulta de NEOs (Near-Earth Objects)

**Classes principais criadas:**
- `CorpoCeleste`: Modelo de domínio simplificado
- `NeoSimplificadoDto`: DTO para resposta simplificada
- `NasaSbdbClient`: Cliente Feign para API da NASA
- `NasaCorpoCelesteService`: Serviço de integração
- `NasaController`: Controller REST para NASA API
- `NasaFeignConfig`: Configurações específicas do Feign
  
Essa feature é consumida pelo projeto da primeira disciplina, o [`stellar-index`](https://github.com/lanzgit/stellar-index), sendo feita uma `feature-05` do mesmo, para sua adaptação.
___

## Feature 01 - TDD
### 🧪 Test-Driven Development - ConstelacaoService
**Implementação completa seguindo metodologia TDD:**
- Desenvolvimento orientado por testes com ciclo Red-Green-Refactor
- Cobertura de testes abrangente para regras de negócio
- Testes unitários com JUnit 5 e assertivas detalhadas
  
### 📋 Funcionalidades Testadas e Implementadas
**1. Modelo de Domínio Constelacao**
- Classe `Constelacao` com atributos:
  - id, nome, abreviacao, quantidadeEstrelas
  - visivelAHumanos, mesesVisiveis
  - Métodos de negócio: ehDoZodiaco() e validações

**2. Service com Regras de Negócio**
- `ConstelacaoService` implementando:
  - `salvar(Constelacao)` - Persistência de constelações
  - `obterConstelacoesPorMes(Month)` - Filtragem por visibilidade sazonal
  - `obterConstelacoesVisiveisAHumanos()` - Filtro de visibilidade humana
  - `obterConstelacoesDoZodiaco()` - Identificação de constelações zodiacais
  - `filtrarConstelacoes(Predicate)` - Método utilitário para filtros

### 🎯 Metodologia TDD Aplicada
**Ciclo Red-Green-Refactor seguido rigorosamente:**
1. **<span style="color:red">RED</span>**: Criação de testes que falham inicialmente
2. **<span style="color:green">GREEN</span>**: Implementação mínima para passar nos testes
3. **<span style="color:blue">REFACTOR</span>**: Melhoria do código mantendo os testes passando
## Estrutura do Projeto
O projeto é um sistema de catálogo de astros organizado em uma arquitetura multi-módulo Maven:

- **vinicius-parent** (pom pai)
  - **common-domain**: Entidades JPA e enums compartilhados
  - **external-api**: Integração com APIs externas (NASA)
  - **main-app**: Aplicação principal com REST API
  
## Principais Funcionalidades Implementadas
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
  - Implementação da interface CrudService<T, ID>
  - ValidacaoService para regras de negócio
  - Integração opcional com API externa
- Repositories JPA:
  - Queries customizadas com @Query
  - Métodos de busca específicos (ex: luas por distância orbital)
- DTOs de Request/Response:
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

## Principais Correções Realizadas
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
  
## Configurações
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

Este projeto implementa um sistema de catalogação de objetos astronômicos com arquitetura proposta de multi-módulos, separação de - responsabilidades, integração externa e API REST documentada.
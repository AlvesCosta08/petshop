# PetShop Project

Este projeto é uma aplicação web para gerenciar clientes, pets, serviços e produtos de um pet shop. Foi desenvolvido utilizando Java com Spring Boot, integrando JPA para persistência de dados e Thymeleaf para as views HTML.

## Estrutura do Projeto

O projeto está estruturado da seguinte forma:



## Tecnologias Utilizadas

- Java 21
- Spring Boot 
- Thymeleaf
- MySQL (ou PostgreSQL, conforme configurado)
- Javascript (Front
- CSS
- HTML5
- BootStrap 5
- 

# Classe SecurityConfig

A classe `SecurityConfig` é responsável por configurar a segurança da sua aplicação Spring Boot. Ela define como o acesso às diferentes partes da aplicação será gerenciado, incluindo autenticação e autorização dos usuários.

## Anotações

- **@Configuration**: Indica que esta classe é uma configuração do Spring e que pode fornecer beans para o contexto da aplicação.
- **@EnableWebSecurity**: Habilita a configuração de segurança para a aplicação.

## Métodos e Beans

### `securityFilterChain(HttpSecurity http)`

Define as regras de segurança para a aplicação:

- **URLs públicas**: Permite acesso sem autenticação a certas URLs, como `/login`, `/logout`, `/`, `/resources`, etc.
- **URLs protegidas**: Exige que o usuário tenha o papel de ADMIN para acessar URLs que começam com `/delete`.
- **Login**: Configura a página de login (`/login`), a página para onde o usuário é redirecionado após um login bem-sucedido (`/dashboard`), e a página de erro de login (`/login?error=true`).
- **Logout**: Configura o URL para logout (`/logout`) e para onde o usuário é redirecionado após o logout (`/logout.html`).
- **Gerenciamento de Sessão**: Configura como as sessões devem ser gerenciadas, incluindo como lidar com sessões inválidas e expiradas, e restringe a uma única sessão por usuário.

### `userDetailsService()`

Cria um gerenciador de usuários em memória com dois usuários:

- **squad3** com o papel **ADMIN**.
- **cliente** com o papel **USER**.

Codifica as senhas dos usuários usando o encoder definido (`passwordEncoder`).

### `passwordEncoder()`

Fornece um encoder de senha (neste caso, `BCryptPasswordEncoder`) para codificar as senhas dos usuários antes de armazená-las.

## Resumo

A classe `SecurityConfig` configura a segurança da sua aplicação, definindo quem pode acessar quais URLs, como os usuários devem fazer login e sair, e gerenciando as sessões dos usuários. Ela também cria alguns usuários em memória para a autenticação e define como as senhas devem ser codificadas.



## Configuração e Execução

Para executar o projeto localmente, siga estas etapas:

1. Clone este repositório.
2. Configure o arquivo `application.properties` com as suas credenciais de banco de dados.
3. Execute o arquivo `PetshopApplication.java` para iniciar a aplicação.
4. Acesse a aplicação em `http://localhost:8080`.

## Testes

Os testes unitários são fornecidos para os serviços e controladores. Para executá-los, utilize sua IDE preferida ou execute `mvn test` na linha de comando.




# AgendamentoController

A classe `AgendamentoController` gerencia os agendamentos na aplicação Pet Shop. Ela fornece funcionalidades para criar, listar, editar e excluir agendamentos para pets e seus donos.

## Endpoints

### 1. Cadastrar Agendamento
- **URL:** `/agendamentos/cadastrar`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para criar um novo agendamento.

### 2. Listar Agendamentos
- **URL:** `/agendamentos/listar`
- **Método:** `GET`
- **Descrição:** Lista todos os agendamentos com seus detalhes.

### 3. Salvar Agendamento
- **URL:** `/agendamentos/salvar`
- **Método:** `POST`
- **Descrição:** Salva um novo agendamento. Valida a entrada e redireciona para a lista de agendamentos ao sucesso.
- **Parâmetros:**
    - `agendamento`: Os detalhes do agendamento a serem salvos.

### 4. Excluir Agendamento
- **URL:** `/agendamentos/excluir/{id}`
- **Método:** `GET`
- **Descrição:** Exclui um agendamento pelo seu ID e redireciona para a lista de agendamentos.
- **Variável de Caminho:**
    - `id`: O ID do agendamento a ser excluído.

### 5. Editar Agendamento
- **URL:** `/agendamentos/editar/{id}`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para editar um agendamento existente.
- **Variável de Caminho:**
    - `id`: O ID do agendamento a ser editado.

### 6. Atualizar Agendamento
- **URL:** `/agendamentos/editar`
- **Método:** `POST`
- **Descrição:** Atualiza um agendamento existente. Valida a entrada e redireciona para a lista de agendamentos ao sucesso.
- **Parâmetros:**
    - `agendamento`: Os detalhes atualizados do agendamento.

## Dependências
- Spring Framework
- Spring Data JPA
- Jakarta Validation

## Serviços Utilizados
- `AgendamentoService`: Gerencia a lógica de negócios relacionada aos agendamentos.
- `ClienteService`: Fornece dados de clientes.
- `PetService`: Fornece dados de pets.
- `ServicoService`: Fornece dados de serviços.

## Atributos do Modelo
- Os seguintes atributos do modelo são populados automaticamente para uso nas views:
    - `cliente`: Lista de todos os clientes.
    - `pet`: Lista de todos os pets.
    - `servico`: Lista de todos os serviços.

## Templates de View
- **Cadastro:** `agendamento/cadastro`
- **Lista:** `agendamento/lista`
- **Editar:** `agendamento/editar`

## Uso
1. Acesse o formulário de cadastro para criar um novo agendamento.
2. Envie o formulário para salvar o agendamento.
3. Visualize todos os agendamentos na lista.
4. Edite ou exclua agendamentos conforme necessário.

# ClienteController

A classe `ClienteController` gerencia as operações relacionadas aos clientes na aplicação Pet Shop. Ela oferece funcionalidades para cadastrar, listar, editar e excluir clientes.

## Endpoints

### 1. Cadastrar Cliente
- **URL:** `/clientes/cadastrar`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para cadastrar um novo cliente.

### 2. Listar Clientes
- **URL:** `/clientes/listar`
- **Método:** `GET`
- **Descrição:** Lista todos os clientes cadastrados.

### 3. Salvar Cliente
- **URL:** `/clientes/salvar`
- **Método:** `POST`
- **Descrição:** Salva um novo cliente. Valida os dados de entrada e redireciona para a lista de clientes ao sucesso.
- **Parâmetros:**
    - `cliente`: Os detalhes do cliente a serem salvos.

### 4. Excluir Cliente
- **URL:** `/clientes/excluir/{id}`
- **Método:** `GET`
- **Descrição:** Exclui um cliente pelo seu ID e redireciona para a lista de clientes.
- **Variável de Caminho:**
    - `id`: O ID do cliente a ser excluído.

### 5. Editar Cliente
- **URL:** `/clientes/editar/{id}`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para editar um cliente existente.
- **Variável de Caminho:**
    - `id`: O ID do cliente a ser editado.

### 6. Atualizar Cliente
- **URL:** `/clientes/editar`
- **Método:** `POST`
- **Descrição:** Atualiza um cliente existente. Valida os dados de entrada e redireciona para a lista de clientes ao sucesso.
- **Parâmetros:**
    - `cliente`: Os detalhes atualizados do cliente.

## Dependências
- Spring Framework
- Spring Data JPA
- Jakarta Validation

## Serviços Utilizados
- `ClienteService`: Gerencia a lógica de negócios relacionada aos clientes.

## Atributos do Modelo
- Os seguintes atributos do modelo são populados automaticamente para uso nas views:
    - `cliente`: Detalhes do cliente para cadastro e edição.

## Templates de View
- **Cadastro:** `cliente/cadastro`
- **Lista:** `cliente/lista`
- **Editar:** `cliente/editar`

## Uso
1. Acesse o formulário de cadastro para criar um novo cliente.
2. Envie o formulário para salvar o cliente.
3. Visualize todos os clientes na lista.
4. Edite ou exclua clientes conforme necessário.

## Observações
- A validação é aplicada aos formulários de entrada para garantir a integridade dos dados.
- Mensagens de sucesso são exibidas após ações de redirecionamento, como salvar ou excluir um cliente.


## Observações
- Certifique-se de que a validação está implementada para manter a integridade dos dados.
- Atributos de redirecionamento são usados para exibir mensagens de sucesso nas ações de redirecionamento.

# PetController

A classe `PetController` gerencia as operações relacionadas aos pets na aplicação Pet Shop. Ela fornece funcionalidades para cadastrar, listar, editar e excluir pets.

## Endpoints

### 1. Cadastrar Pet
- **URL:** `/pets/cadastrar`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para cadastrar um novo pet.

### 2. Listar Pets
- **URL:** `/pets/listar`
- **Método:** `GET`
- **Descrição:** Lista todos os pets cadastrados.

### 3. Salvar Pet
- **URL:** `/pets/salvar`
- **Método:** `POST`
- **Descrição:** Salva um novo pet. Valida os dados de entrada e redireciona para a lista de pets ao sucesso.
- **Parâmetros:**
    - `pet`: Os detalhes do pet a serem salvos.

### 4. Excluir Pet
- **URL:** `/pets/excluir/{id}`
- **Método:** `GET`
- **Descrição:** Exclui um pet pelo seu ID e redireciona para a lista de pets.
- **Variável de Caminho:**
    - `id`: O ID do pet a ser excluído.

### 5. Editar Pet
- **URL:** `/pets/editar/{id}`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para editar um pet existente.
- **Variável de Caminho:**
    - `id`: O ID do pet a ser editado.

### 6. Atualizar Pet
- **URL:** `/pets/editar`
- **Método:** `POST`
- **Descrição:** Atualiza um pet existente. Valida os dados de entrada e redireciona para a lista de pets ao sucesso.
- **Parâmetros:**
    - `pet`: Os detalhes atualizados do pet.

## Dependências
- Spring Framework
- Spring Data JPA
- Jakarta Validation

## Serviços Utilizados
- `PetService`: Gerencia a lógica de negócios relacionada aos pets.
- `ClienteService`: Fornece dados dos clientes para associação com pets.

## Atributos do Modelo
- O seguinte atributo do modelo é populado automaticamente para uso nas views:
    - `cliente`: Lista de todos os clientes.

## Templates de View
- **Cadastro:** `pet/cadastro`
- **Lista:** `pet/lista`
- **Editar:** `pet/editar`

## Uso
1. Acesse o formulário de cadastro para criar um novo pet.
2. Envie o formulário para salvar o pet.
3. Visualize todos os pets na lista.
4. Edite ou exclua pets conforme necessário.

## Observações
- A validação é aplicada aos formulários de entrada para garantir a integridade dos dados.
- Mensagens de sucesso são exibidas após ações de redirecionamento, como salvar ou excluir um pet.


# ProdutoController

A classe `ProdutoController` gerencia as operações relacionadas aos produtos na aplicação Pet Shop. Ela fornece funcionalidades para cadastrar, listar, editar e excluir produtos.

## Endpoints

### 1. Cadastrar Produto
- **URL:** `/produtos/cadastrar`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para cadastrar um novo produto.

### 2. Listar Produtos
- **URL:** `/produtos/listar`
- **Método:** `GET`
- **Descrição:** Lista todos os produtos cadastrados, com paginação.
- **Parâmetros:**
    - `page`: Número da página (padrão: 0).
    - `size`: Número de produtos por página (padrão: 9).

### 3. Salvar Produto
- **URL:** `/produtos/salvar`
- **Método:** `POST`
- **Descrição:** Salva um novo produto. Valida os dados de entrada, salva a imagem se fornecida e redireciona para a lista de produtos ao sucesso.
- **Parâmetros:**
    - `produto`: Os detalhes do produto a serem salvos.
    - `file`: Arquivo da imagem do produto.

### 4. Excluir Produto
- **URL:** `/produtos/excluir/{id}`
- **Método:** `GET`
- **Descrição:** Exclui um produto pelo seu ID e redireciona para a lista de produtos.
- **Variável de Caminho:**
    - `id`: O ID do produto a ser excluído.

### 5. Editar Produto
- **URL:** `/produtos/editar/{id}`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para editar um produto existente.
- **Variável de Caminho:**
    - `id`: O ID do produto a ser editado.

### 6. Atualizar Produto
- **URL:** `/produtos/editar`
- **Método:** `POST`
- **Descrição:** Atualiza um produto existente. Valida os dados de entrada, salva a nova imagem se fornecida e redireciona para a lista de produtos ao sucesso.
- **Parâmetros:**
    - `produto`: Os detalhes atualizados do produto.
    - `file`: Arquivo da nova imagem do produto (opcional).
    - `fotoOriginal`: URL da imagem original (se a nova imagem não for fornecida).

## Dependências
- Spring Framework
- Spring Data JPA
- Jakarta Validation

## Serviços Utilizados
- `ProdutoService`: Gerencia a lógica de negócios relacionada aos produtos.

## Atributos do Modelo
- Nenhum atributo do modelo específico, mas a classe `Produto` é utilizada para transferir dados.

## Templates de View
- **Cadastro:** `produto/cadastro`
- **Lista:** `produto/lista`
- **Editar:** `produto/editar`

## Uso
1. Acesse o formulário de cadastro para criar um novo produto.
2. Envie o formulário para salvar o produto.
3. Visualize todos os produtos na lista.
4. Edite ou exclua produtos conforme necessário.

## Observações
- A validação é aplicada aos formulários de entrada para garantir a integridade dos dados.
- Mensagens de sucesso são exibidas após ações de redirecionamento, como salvar ou excluir um produto.
- Arquivos de imagem são salvos no diretório configurado em `file.upload-dir`.

# ServicoController

A classe `ServicoController` gerencia as operações relacionadas aos serviços na aplicação Pet Shop. Ela fornece funcionalidades para cadastrar, listar, editar e excluir serviços.

## Endpoints

### 1. Cadastrar Serviço
- **URL:** `/servicos/cadastrar`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para cadastrar um novo serviço.

### 2. Listar Serviços
- **URL:** `/servicos/listar`
- **Método:** `GET`
- **Descrição:** Lista todos os serviços cadastrados.

### 3. Salvar Serviço
- **URL:** `/servicos/salvar`
- **Método:** `POST`
- **Descrição:** Salva um novo serviço. Valida os dados de entrada e redireciona para a lista de serviços ao sucesso.
- **Parâmetros:**
    - `servico`: Os detalhes do serviço a serem salvos.

### 4. Excluir Serviço
- **URL:** `/servicos/excluir/{id}`
- **Método:** `GET`
- **Descrição:** Exclui um serviço pelo seu ID e redireciona para a lista de serviços.
- **Variável de Caminho:**
    - `id`: O ID do serviço a ser excluído.

### 5. Editar Serviço
- **URL:** `/servicos/editar/{id}`
- **Método:** `GET`
- **Descrição:** Exibe o formulário para editar um serviço existente.
- **Variável de Caminho:**
    - `id`: O ID do serviço a ser editado.

### 6. Atualizar Serviço
- **URL:** `/servicos/editar`
- **Método:** `POST`
- **Descrição:** Atualiza um serviço existente. Valida os dados de entrada e redireciona para a lista de serviços ao sucesso.
- **Parâmetros:**
    - `servico`: Os detalhes atualizados do serviço.

## Dependências
- Spring Framework
- Spring Data JPA
- Jakarta Validation

## Serviços Utilizados
- `ServicoService`: Gerencia a lógica de negócios relacionada aos serviços.

## Templates de View
- **Cadastro:** `servico/cadastro`
- **Lista:** `servico/lista`
- **Editar:** `servico/editar`

## Uso
1. Acesse o formulário de cadastro para criar um novo serviço.
2. Envie o formulário para salvar o serviço.
3. Visualize todos os serviços na lista.
4. Edite ou exclua serviços conforme necessário.

## Observações
- A validação é aplicada aos formulários de entrada para garantir a integridade dos dados.
- Mensagens de sucesso ou falha são exibidas após ações de redirecionamento, como salvar ou excluir um serviço.


# Acesso ao Swagger UI

Para acessar a documentação da API gerada pelo Swagger UI em sua aplicação Spring Boot, siga estes passos:

### 1. Certifique-se de que a aplicação está rodando

Primeiro, verifique se a sua aplicação PetShop está em execução. Se você estiver utilizando uma IDE, como IntelliJ IDEA ou Eclipse, inicie a aplicação a partir da IDE. Se estiver usando a linha de comando, execute o comando apropriado para iniciar a aplicação, por exemplo:

#### Swagger UI: http://localhost:8080/swagger-ui.html

```bash
mvn spring-boot:run

```
## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir um pull request ou reportar problemas através das issues.

## Licença

Este projeto está licenciado sob a [MIT License](https://opensource.org/licenses/MIT).
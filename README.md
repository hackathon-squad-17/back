### Entendendo e executando a aplicação :rocket:

#### Requisitos e informações

Antes de mais nada, para executar e editar essa aplicação, você precisará de:

:computer_mouse: JDK (Java Runtime Environment, foi utilizada a versão 11.0.14.1 no desenvolvimento)

:computer_mouse: Uma IDE Java instalada

:computer_mouse: O banco de dados PostgreSQL instalado

Para acessar o Swagger da aplicação, o endereço http://localhost:8080/swagger-ui.html#/ deve ser acessado no navegador enquanto a aplicação estiver em execução.

#### Detalhes da aplicação

A aplicação contém três classes que são mapeadas como entidades para o banco de dados PostgreSQL. Toda a integração com o banco é feita utilizando JPA com Hibernate.

:bust_in_silhouette: A classe Usuário é responsável por armazenar as informações do usuário em uma entidade no banco de dados.

:triangular_flag_on_post: A classe Postagem é responsável por armazenar as postagens em uma entidade no banco de dados. Além disso, ela possui uma relação bidirecional com a classe Usuário, para conectar cada postagem ao usuário que a escreveu.

:pencil2: A classe Comentario é responsável por armazenar os comentários feitos em postagens em uma entidade no banco de dados. Ela possui uma relação unidirecional com a classe Usuário, para vincular um comentário ao usuário que o escreveu, e o método Post (que faz a criação de um novo comentário), que está em sua Controller, faz a ligação entre um comentário e a postagem a qual ele pertence.

:bust_in_silhouette: :triangular_flag_on_post: :pencil2: :passport_control: Todas as respectivas Controllers possuem os métodos GET e POST para suas Classes. Na Controller de Postagem está incluído, ainda, um método que é capaz retornar as postagens de um usuário específico.

#### Autores 




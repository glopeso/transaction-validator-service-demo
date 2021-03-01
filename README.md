### Preparando o ambiente de desenvolvimento
Siga os passos abaixo antes de iniciar o projeto localmente.

Pelas inumeras vantagens que container nos oferece para agilizar o desenvolvimento e validação do projeto antes de produção optamos por usar o Kafka e o DynamoDB em Docker.
 
A nível de desenvolvimento optamos por utilizar o Kafka em Docker 

Para não precisar criar tabelas em uma ambiente amazon e pagar por isso, optamos por utilizar o DynamoDB em Docker seguindo a documentação abaixo:

* [Executando DynamoDB Local](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html)

Após criar e subir a imagem Docker do DynamoDB, crie as tabelas via lia de comando:

- Tabela que contem as informações de clientes com restrições ex (Lista de terroristas):

`aws dynamodb --endpoint-url http://localhost:8000 create-table --table-name customer-restriction \
 --attribute-definitions AttributeName=customer_id,AttributeType=S --key-schema AttributeName=customer_id,KeyType=HASH \
 --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5`
 
 

### Documentação de referência
Por favor considere as documentações abaixo para seguir as melhores praticas:

* [Documentação oficial Gradle](https://docs.gradle.org)
* [Spring Boot Gradle Plugin](https://docs.spring.io/spring-boot/docs/2.4.3/gradle-plugin/reference/html/)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-kafka)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [DynamoDB SDK](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-dynamodb.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)


# Hackathon Lince Tech 2024 - 1

## Instruções de configuração do ambiente

### Downloads

Para executar o projeto, é necessário ter instalado em seu ambiente:

- Distribuição da JDK versão 11 (ex.: _Eclipse Temurin OpenJDK_, _RedHat OpenJDK_, _Amazon Corretto OpenJDK_, etc)
- Distribuição do TomCat versão 9 ([download](https://tomcat.apache.org/download-90.cgi))
- **Opcional**: _Ferramenta de desenvolvimento com suporte a Java e Gradle_
    - IntelliJ IDEA (ex.: _IDEA Community / Ultimate / Ultimate EAP_): [download](https://www.jetbrains.com/idea/)
    - _Eclipse IDE_: [download](https://eclipseide.org/)
    - _VS Code_: [download](https://code.visualstudio.com/download)

### Instruções de configuração do ambiente

#### Gradle e TomCat

Feito o download do TomCat, geralmente distribuído em formato ".zip" é necessário extrair para uma pasta e apontar o
local onde o TomCat foi extraído no arquivo `build.gradle`. Exemplo:

Considerando o tomcat extraído na pasta "C:/dev/tools/tomcat9", é necessário atualizar o item `cargo` no
arquivo `build.gradle` conforme exemplo abaixo:

```groovy
// Configurar plugin utilizado para executar a aplicação utilizando Gradle
cargo {
    // User TomCat 9 (ultima versão compatível com Java 11)
    containerId = 'tomcat9x'
    port = 9090

    // Configurações da aplicação
    deployable {
        // Caminho raiz do programa
        context = 'hackathon'
    }

    // Configurações do ambiente de execução
    local {
        // Caminho da instalação do TomCat
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        homeDir = file('C:\\dev\\tools\\tomcat9')
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        // Definir que a JVM deve usar encoding UTF-8
        jvmArgs = '-Dfile.encoding=utf-8'

        // Caminho do arquivo de logs de saida
        outputFile = file('build\\output.log')

        // Tempo limite para iniciar/interromper
        startStopTimeout = 60000

        // Configurações do TomCat
        containerProperties {
            property 'cargo.tomcat.ajp.port', 9099
        }
    }
}
```

#### Variáveis de ambiente

Para executar o programa, é necessário configurar as variáveis de ambiente que definem as informações para conexão
ao banco de dados.

| Variável              | Descrição                                                    |
|-----------------------|--------------------------------------------------------------|
| **DATABASE_NAME**     | Nome do banco de dados a ser utilizado em sua aplicação      |
| **DATABASE_SERVER**   | Endereço ou IP do servidor de SQL com o seu banco de dados   |
| **DATABASE_USER**     | Credencial: nome de usuário da conexão com o banco de dados  |
| **DATABASE_PASSWORD** | Credencial: senha do usuário da conexão com o banco de dados |
| **OPEN_AI_KEY**       | _**Opcional**: Chave de acesso a API paga da OpenIA_         |

**Obs.**: Esse modelo não deve ser replicado para ambientes de produção. Em ambientes sensíveis esse tipo de informação
deve ser tratado utilizando ferramentas de controle de segredos, que aplicam criptografia e garantem um nível adequado
de proteção a suas credenciais.

### Configurar banco de dados

O projeto foi configurado para trabalhar utilizando o Microsoft SQL Server 2022, mas para testes em seu ambiente você
pode utilizar o [Microsoft SQL Server Express](https://www.microsoft.com/pt-br/sql-server/sql-server-downloads), ou caso
prefira é possível atualizar o projeto para executar com outro SGBD.

Para utilizar outro SGBD, será necessário atualizar o arquivo `build.gradle` para incluir a biblioteca contendo as
bibliotecas dos drivers de conexão do banco de dados desejado no objeto `dependencies`, por exemplo para utilizar um
banco de dados [PostgreSQL](https://www.postgresql.org/):

```groovy
dependencies {
    // REMOVER
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.6.2.jre11")

    // INCLUIR
    implementation("org.postgresql:postgresql:42.7.3")
}
```

Também será necessário alterar a classe de driver carregada no
arquivo [JDBIConnection.java](src%2Fmain%2Fjava%2Fbr%2Fcom%2Flince%2Fhackathon%2Fstandard%2FJDBIConnection.java) e 
alterar a string de conexão:

```java
public class JDBIConnection {
    ...

    public static Jdbi instance() {
        try {
            // REMOVER
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // INCLUIR
            Class.forName("org.postgresql.Driver");

            ...

            // REMOVER
            final var connection = DriverManager.getConnection(
                    "jdbc:sqlserver://" + server
                            + ";SelectMethod=cursor;sendStringParametersAsUnicode=false;responseBuffering=adaptive;"
                            + "User=" + user
                            + ";PassWord=" + password
                            + ";DataBaseName=" + database
                            + ";encrypt=false"
            );

            // INCLUIR
            final var connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + server + ":5432" + "/" + database, 
                    user,
                    password
            );
          
            ...
        }
    }
```

Além dessas alterações, será necessário rever os comandos SQL definidos nas classes pertinentes ao JDBI, e scripts de
criação de tabela.
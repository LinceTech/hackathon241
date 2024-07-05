CREATE TABLE clientes
(
    id int   NOT NULL IDENTITY(1,1),
    nome VARCHAR(60)  NOT NULL DEFAULT '',
    cpf char(14) NOT NULL DEFAULT '',
    data_nascimento DATE NOT NULL DEFAULT  '',
    telefone INT NOT NULL DEFAULT 0,
    email VARCHAR(60) NOT NULL DEFAULT '',
    CEP CHAR(9) NOT NULL DEFAULT '',
    cidade VARCHAR(60) NOT NULL DEFAULT '',
    estado CHAR(2) NOT NULL DEFAULT '',
    bairro VARCHAR (60) NOT NULL DEFAULT '',
    rua VARCHAR (60) NOT NULL DEFAULT '',
    numero INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);
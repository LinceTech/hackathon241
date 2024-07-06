CREATE TABLE tb_clientes (
    id INTEGER              NOT NULL IDENTITY (1, 1),
    nome VARCHAR(255)       NOT NULL,
    cpf CHAR(14)            NOT NULL UNIQUE,
    data_nascimento DATE    NOT NULL,
    telefone VARCHAR(15)    NOT NULL,
    email VARCHAR(255)      NOT NULL,
    cep CHAR(9)             NOT NULL,
    cidade VARCHAR(255)     NOT NULL,
    estado CHAR(2)          NOT NULL,
    bairro VARCHAR(255)     NOT NULL,
    rua VARCHAR(255)        NOT NULL,
    numero INTEGER          NOT NULL,

    PRIMARY KEY (id)
);

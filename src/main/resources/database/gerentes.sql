-- Comando para criação da tabela 'gerentes', utilizando '' como identidade do registro
CREATE TABLE tb_gerentes
(
    id INTEGER      NOT NULL IDENTITY (1, 1),
    nome VARCHAR(255)  NOT NULL DEFAULT '',
    cpf VARCHAR(11) NOT NULL DEFAULT '',
    telefone VARCHAR(20) NOT NULL DEFAULT '',
    email VARCHAR(255) NOT NULL DEFAULT '',
    cidade VARCHAR(255) NOT NULL DEFAULT '',
    estado VARCHAR(255) NOT NULL DEFAULT '',
    percentual_comissao FLOAT(5) NOT NULL DEFAULT '',
    data_contratacao DATE NOT NULL DEFAULT '',

    PRIMARY KEY (id)
);
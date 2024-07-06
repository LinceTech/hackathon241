-- Comando para criação da tabela 'gerente', utilizando 'bar' como identidade do registro
CREATE TABLE gerente
(
    id integer not null identity(1,1) primary key,
    nome VARCHAR(60) NOT NULL default (''),
    cpf numeric(11) NOT NULL default (0),
    telefone numeric(11)  NOT NULL default (0),
    email varchar(60) NOT NULL default (''),
    cidade varchar(60) NOT NULL default (''),
    estado varchar(60) NOT NULL default (''),
    percentualComissao numeric (4,2)  NOT NULL default (0),
    dataContratacao numeric(8)  NOT NULL default (0)
);


INSERT INTO gerente(nome, cpf, telefone, email, cidade, estado, percentualComissao, dataContratacao)
VALUES
('teste11', 12345678919, 6545498411, 'teste@teste', 'gaspar', 'sc', '12', 20240205)
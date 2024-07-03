-- Comando para criação da tabela 'foo', utilizando 'bar' como identidade do registro
CREATE TABLE foo
(
    bar INTEGER      NOT NULL IDENTITY (1, 1),
    bas VARCHAR(60)  NOT NULL DEFAULT '',
    boo VARCHAR(MAX) NOT NULL DEFAULT '',

    PRIMARY KEY (bar)
);
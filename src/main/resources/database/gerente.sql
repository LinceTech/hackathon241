create table gerente (
    id INTEGER NOT NULL IDENTITY (1,1),
    Nome VARCHAR(60) NOT NULL,
    CPF CHAR(14) NOT NULL,
    Telefone CHAR(15) NOT NULL,
    Email VARCHAR(60) NOT NULL,
    Cidade VARCHAR(60) NOT NULL,
    Estado VARCHAR(60) NOT NULL,
    PercentualDeComissao FLOAT NOT NULL,
    DataDeContratacao DATE NOT NULL,
    PRIMARY KEY (id)
)
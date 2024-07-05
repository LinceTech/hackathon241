CREATE TABLE cliente
(
    id             INTEGER     NOT NULL IDENTITY(1,1) PRIMARY KEY,
    nome           VARCHAR(60) NOT NULL DEFAULT '',
    cpf            NUMERIC(11) NOT NULL DEFAULT 0,
    dataNascimento NUMERIC(8)  NOT NULL DEFAULT 0,
    telefone       NUMERIC(11) NOT NULL DEFAULT 0,
    email          VARCHAR(60) NOT NULL DEFAULT '',
    cep            NUMERIC(8)  NOT NULL DEFAULT 0,
    cidade         VARCHAR(60) NOT NULL DEFAULT '',
    estado         VARCHAR(60) NOT NULL DEFAULT '',
    bairro         VARCHAR(60) NOT NULL DEFAULT '',
    rua            VARCHAR(60) NOT NULL DEFAULT '',
    numero         NUMERIC(5)  NOT NULL DEFAULT 0
);

CREATE TABLE locacao
(

    id                  INTEGER NOT NULL IDENTITY(1, 1)  PRIMARY KEY,
    constraint locacaoVeiculo FOREIGN KEY (id) references veiculo (id),
    constraint locacaoCliente FOREIGN KEY (id) references cliente (id),
    constraint locacaoGerente FOREIGN KEY (id) references gerente (id),
    dataInicio         NUMERIC(8)          NOT NULL DEFAULT (0),
    dataEntrega        NUMERIC(8)          NOT NULL DEFAULT (0),
    valorDiaria        NUMERIC(9, 2)       NOT NULL DEFAULT (0),
    percentualComissao NUMERIC(4, 2)       NOT NULL DEFAULT (0),
    valorTotalPago     NUMERIC(9, 2)       NOT NULL DEFAULT (0),
    dataPagamento      NUMERIC(8)          NOT NULL DEFAULT (0)
)
CREATE TABLE Gerente (
    Id int NOT NULL IDENTITY (1,1) PRIMARY KEY,
    Nome varchar(60) NOT NULL,
    CPF decimal(11,0) NOT NULL,
    Telefone decimal(13,0) NOT NULL,
    Email varchar(256) NOT NULL,
    Cidade varchar(60) NOT NULL,
    Estado varchar(2) NOT NULL,
    Comissao decimal(4,1) NOT NULL CHECK (Comissao >= 0),
    DtContrata decimal(8,0)
);
CREATE TABLE Cliente (
    Id int NOT NULL IDENTITY (1,1) PRIMARY KEY,
    Nome varchar(60) NOT NULL,
    CPF decimal(11,0) NOT NULL,
    DtNascimento decimal(8,0) NOT NULL,
    Telefone decimal(13,0) NOT NULL,
    Email varchar(256) NOT NULL,
    CEP decimal(8,0) NOT NULL,
    Cidade varchar(60) NOT NULL,
    Estado varchar(2) NOT NULL,
    Bairro varchar(60) NOT NULL,
    Rua varchar(60) NOT NULL,
    Numero decimal(6,0) NOT NULL
);
CREATE TABLE Veiculo (
    Id int NOT NULL IDENTITY (1,1) PRIMARY KEY,
    Marca varchar(30) NOT NULL,
    Modelo varchar(60) NOT NULL,
    Placa varchar(7) NOT NULL UNIQUE,
    Cor varchar(30) NOT NULL,
    AnoFabrica decimal(4,0) NOT NULL,
    CustoDiaria decimal(7,2) NOT NULL CHECK (CustoDiaria > 0),
    Descricao varchar(8000) NOT NULL DEFAULT '',
    TipoCombustivel decimal(1,0) NOT NULL
);
CREATE TABLE Locacao (
    Id int NOT NULL IDENTITY (1,1) PRIMARY KEY,
    Cliente int NOT NULL FOREIGN KEY REFERENCES Cliente(Id),
    Gerente int NOT NULL FOREIGN KEY REFERENCES Gerente(Id),
    Veiculo int NOT NULL FOREIGN KEY REFERENCES Veiculo(Id),
    DtInicio decimal(8,0) NOT NULL,
    DtFinal  decimal(8,0),
    CustoDiaria decimal(7,2) NOT NULL CHECK (CustoDiaria > 0),
    ComissaoGerente decimal(4,1) NOT NULL CHECK (ComissaoGerente >= 0),
    ValorTotal decimal(8,2),
    DtPagamento decimal(8,0)
);

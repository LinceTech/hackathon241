create table veiculo (
    id INTEGER NOT NULL IDENTITY (1,1),
    Marca VARCHAR(60) NOT NULL,
    Modelo VARCHAR(60) NOT NULL,
    Placa VARCHAR(8) NOT NULL,
    Cor VARCHAR(60) NOT NULL,
    AnoDeFabricacao INTEGER NOT NULL,
    CustoDeDiaria FLOAT NOT NULL,
    DescricaoPromocional VARCHAR(MAX) NOT NULL,
    TipoDeCombustivel INTEGER NOT NULL,
    PRIMARY KEY (id)
)
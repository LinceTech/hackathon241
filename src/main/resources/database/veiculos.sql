-- Comando para criação da tabela 'veiculo', utilizando 'bar' como identidade do registro
CREATE TABLE veiculo
(
    id integer not null identity(1,1) primary key,
    marca              numeric(2)  NOT NULL default (0),
    modelo             VARCHAR(20) NOT NULL default (''),
    placa              VARCHAR(8) NOT NULL default (''),
    cor                numeric(2) NOT NULL default (0),
    anoDeFabricação    numeric(4) NOT NULL default (0),
    custoDeDiaria      numeric(9, 2) NOT NULL default (0),
    descriçãoPromocional varchar(max) NOT NULL default (''),
    tipoDeCombustivel    numeric(2) NOT NULL default (0)
);

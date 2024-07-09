GO
ALTER TABLE locacao DROP
    CONSTRAINT locacaoCliente
GO
GO
ALTER TABLE locacao DROP
    CONSTRAINT locacaoGerente
GO
GO
ALTER TABLE locacao DROP
    CONSTRAINT locacaoVeiculo
GO
ALTER TABLE locacao
    add gerenteCpf numeric(11) not null default 0
ALTER TABLE locacao
    add clienteCpf numeric(11) not null default 0
ALTER TABLE locacao
    add placaVeiculo varchar(8) not null default ''
alter table gerente add primary key (cpf, id)
alter table cliente add primary key (cpf, id)
alter table veiculo add primary key (placa, id)
ALTER TABLE locacao
    ADD FOREIGN KEY (clienteCpf) REFERENCES cliente(cpf);
ALTER TABLE locacao
    ADD FOREIGN KEY (gerenteCpf) REFERENCES gerente(cpf);
ALTER TABLE locacao
    ADD FOREIGN KEY (placaVeiculo) REFERENCES veiculo(placa);


-- add new auto incremented field
ALTER TABLE dbo.gerente
    ADD id BIGINT IDENTITY;
GO
ALTER TABLE dbo.veiculo
    ADD id BIGINT IDENTITY;
GO
ALTER TABLE dbo.cliente
    ADD id BIGINT IDENTITY;
GO
ALTER TABLE dbo.locacao
    ADD id BIGINT IDENTITY;
GO

ALTER TABLE dbo.gerente
    ADD CONSTRAINT PK_gerenteCpf PRIMARY KEY NONCLUSTERED (cpf);
GO
ALTER TABLE dbo.cliente
    ADD CONSTRAINT PK_clienteCpf PRIMARY KEY NONCLUSTERED (cpf);
GO
ALTER TABLE dbo.veiculo
    ADD CONSTRAINT PK_veiculoPlaca PRIMARY KEY NONCLUSTERED (placa);
GO
select * from gerente (nolock)

ALTER TABLE dbo.locacao
    ADD CONSTRAINT PK_locacao PRIMARY KEY NONCLUSTERED (id);
GO
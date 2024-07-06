CREATE TABLE tb_veiculos (
    id INT IDENTITY (1,1) PRIMARY KEY,
    marca NVARCHAR(100) NOT NULL,
    modelo NVARCHAR(100) NOT NULL,
    placa NVARCHAR(10) NOT NULL UNIQUE,
    cor NVARCHAR(50) NOT NULL,
    ano_fabricacao INT NOT NULL,
    custo_diaria DECIMAL(10, 2) NOT NULL,
    descricao_promocional TEXT,
    tipo_combustivel NVARCHAR(20)
);

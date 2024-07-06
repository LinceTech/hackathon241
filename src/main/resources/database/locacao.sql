CREATE TABLE locacoes (
    id INT PRIMARY KEY IDENTITY(1,1),
    id_cliente INT NOT NULL,
    id_gerente INT NOT NULL,
    id_veiculo INT NOT NULL,
    data_inicio DATE NOT NULL,
    data_entrega DATE,
    valor_diaria DECIMAL(10, 2) NOT NULL,
    percentual_comissao DECIMAL(5, 2),
    valor_total_pago DECIMAL(10, 2),
    data_pagamento DATE,
    CONSTRAINT FK_locacoes_Cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    CONSTRAINT FK_locacoes_Gerente FOREIGN KEY (id_gerente) REFERENCES gerente(id),
    CONSTRAINT FK_locacoes_Veiculo FOREIGN KEY (id_veiculo) REFERENCES veiculo(id)
);
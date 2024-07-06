-- Comando para criação da tabela 'locacao, utilizando '' como identidade do registro
CREATE TABLE tb_locacoes
(
    id INTEGER  NOT NULL IDENTITY (1, 1),
    cliente INTEGER  NOT NULL,
    gerente_responsavel  INTEGER NOT NULL,
    veiculo INTEGER NOT NULL,
    data_inicio DATE NOT NULL DEFAULT '',
    data_entrega_veiculo DATE NOT NULL DEFAULT '',
    valor_diaria FLOAT(10) NOT NULL DEFAULT '',
    percentual_comissao_gerente FLOAT(5) NOT NULL DEFAULT '',
    valor_total_pago FLOAT(10) NOT NULL DEFAULT '',
    data_pagamento DATE NOT NULL DEFAULT '',

    PRIMARY KEY (id)
);
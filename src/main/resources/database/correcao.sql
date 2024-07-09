EXEC sp_RENAME 'veiculo.descriçãoPromocional' ,
     'descricaoPromocional', 'COLUMN'
ALTER
    TABLE veiculo RENAME COLUMN anoDeFabricação to anoDeFabricacao;
ALTER TABLE veiculo
    RENAME COLUMN descriçãoPromocional to descricaoPromocional;
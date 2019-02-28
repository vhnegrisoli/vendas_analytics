-- INSERTS DO SISTEMA

-- REGIÃO

 	Insert into Regiao (Id, Nome) values (1, 'Norte');
	Insert into Regiao (Id, Nome) values (2, 'Nordeste');
	Insert into Regiao (Id, Nome) values (3, 'Sudeste');
	Insert into Regiao (Id, Nome) values (4, 'Sul');
	Insert into Regiao (Id, Nome) values (5, 'Centro-Oeste');

-- ESTADOS
	
	Insert into Estado (codigo_ibge, estado, sigla, Regiao_id) values (12, 'Acre', 'AC', 1);
	Insert into Estado (codigo_ibge, estado, sigla, Regiao) values (27, 'Alagoas', 'AL', 2);
	Insert into Estado (codigo_ibge, estado, sigla, Regiao) values (16, 'Amapá', 'AP', 1);
	Insert into Estado (codigo_ibge, estado, sigla, Regiao) values (13, 'Amazonas', 'AM', 1);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (29, 'Bahia', 'BA', 2);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (23, 'Ceará', 'CE', 2);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (53, 'Distrito Federal', 'DF', 5);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (32, 'Espírito Santo', 'ES', 3);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (52, 'Goiás', 'GO', 5);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (21, 'Maranhão', 'MA', 2);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (51, 'Mato Grosso', 'MT', 5);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (50, 'Mato Grosso do Sul', 'MS', 5);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (31, 'Minas Gerais', 'MG', 3);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (15, 'Pará', 'PA', 1);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (25, 'Paraíba', 'PB', 2);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (41, 'Paraná', 'PR', 4);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (26, 'Pernambuco', 'PE', 2);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (22, 'Piauí', 'PI', 2);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (33, 'Rio de Janeiro', 'RJ', 3);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (24, 'Rio Grande do Norte', 'RN', 2);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (43, 'Rio Grande do Sul', 'RS', 4);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (11, 'Rondônia', 'RO', 1);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (14, 'Roraima', 'RR', 1);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (42, 'Santa Catarina', 'SC', 4);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (35, 'São Paulo', 'SP', 3);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (28, 'Sergipe', 'SE', 2);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (17, 'Tocantins', 'TO', 1);
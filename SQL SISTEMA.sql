-- INSERTS DO SISTEMA

-- REGIÃO

 	Insert into Regiao (Nome) values ('Norte');
	Insert into Regiao (Nome) values ('Nordeste');
	Insert into Regiao (Nome) values ('Sudeste');
	Insert into Regiao (Nome) values ('Sul');
	Insert into Regiao (Nome) values ('Centro-Oeste');

-- ESTADOS
	
	
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (12, 'Acre', 'AC', 1);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (27, 'Alagoas', 'AL', 2);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (16, 'Amapá', 'AP', 1);
	Insert into Estado (codigo_ibge, estado, sigla, regiao_id) values (13, 'Amazonas', 'AM', 1);
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
	
	-- CIDADES
	
	INSERT INTO CIDADE (CIDADE, ESTADO_ID) VALUES ('Londrina', 16);
	INSERT INTO CIDADE (CIDADE, ESTADO_ID) VALUES ('Curitiba', 16);
	INSERT INTO CIDADE (CIDADE, ESTADO_ID) VALUES ('Ponta Grossa', 16);
	INSERT INTO CIDADE (CIDADE, ESTADO_ID) VALUES ('São Paulo', 25);
	INSERT INTO CIDADE (CIDADE, ESTADO_ID) VALUES ('Rio de Janeiro', 19);

	-- ENDERECO
	INSERT INTO ENDERECO (CEP, NUMERO, RUA, CIDADE_ID, ESTADO_ID, COMPLEMENTO)
	VALUES ('86.050-523', 80, 'Rua Tereza Zanetti Lopes', 1, 16, 'Apto. 706');
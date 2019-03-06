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
	
	
	-- CATEGORIAS
	
	INSERT INTO CATEGORIA (DESCRICAO, TIPO) VALUES ('Livros', 'LIVROS');
	INSERT INTO CATEGORIA (DESCRICAO, TIPO) VALUES ('Software', 'SOFTWARE');
	INSERT INTO CATEGORIA (DESCRICAO, TIPO) VALUES ('Papelaria', 'PAPELARIA');
	INSERT INTO CATEGORIA (DESCRICAO, TIPO) VALUES ('Alimentos', 'ALIMENTOS');
	INSERT INTO CATEGORIA (DESCRICAO, TIPO) VALUES ('Ferramentas', 'FERRAMENTAS');
	INSERT INTO CATEGORIA (DESCRICAO, TIPO) VALUES ('Cursos', 'CURSOS');
	INSERT INTO CATEGORIA (DESCRICAO, TIPO) VALUES ('Filmes', 'FILMES');
	INSERT INTO CATEGORIA (DESCRICAO, TIPO) VALUES ('Discos', 'DISCOS');
	INSERT INTO CATEGORIA (DESCRICAO, TIPO) VALUES ('Eletrônicos', 'ELETRONICOS');
	
	-- FORNECEDORES
	
	INSERT INTO FORNECEDOR (CNPJ, ENDERECO, NOME_FANTASIA, RAZAO_SOCIAL)
	VALUES ('61.365.284/0001-04', 'RUA HENRIQUE SCHAUMANN, 270', 'LIVRARIA SARAIVA', 'SARAIVA E SICILIANO S/A');

	INSERT INTO FORNECEDOR (CNPJ, ENDERECO, NOME_FANTASIA, RAZAO_SOCIAL)
	VALUES ('05.570.714/0001-59 ', '-', 'KaBuM!', 'KABUM COMÉRCIO ELETRÔNICO S/A');

	INSERT INTO FORNECEDOR (CNPJ, ENDERECO, NOME_FANTASIA, RAZAO_SOCIAL)
	VALUES ('79.065.181.0001-94 ', 'Avenida Marechal Floriano Peixoto, nº 1762 - Rebouças ', 'Livrarias Curitiba', 'Distribuidora Curitiba de Papéis e Livros SA');

	INSERT INTO FORNECEDOR (CNPJ, ENDERECO, NOME_FANTASIA, RAZAO_SOCIAL)
	VALUES ('00.776.574/0006-60', 'Rua Sacadura Cabral, 102, Bairro Saúde, 270', 'Lojas Americanas', 'B2W – Companhia Digital');

	-- PRODUTOS
	
	INSERT INTO PRODUTO (DESCRICAO, NOME_PRODUTO, PRECO, CATEGORIA_ID, FORNECEDOR_ID)
	VALUES ('História em Quadrinhos - Editora Panini Comics', 'Crise nas Infinitas Terras', 110.00, 1, 1);

	INSERT INTO PRODUTO (DESCRICAO, NOME_PRODUTO, PRECO, CATEGORIA_ID, FORNECEDOR_ID)
	VALUES ('História em Quadrinhos - Editora Panini Comics', 'Superman: O Homem de Aço', 56.70, 1, 1);

	INSERT INTO PRODUTO (DESCRICAO, NOME_PRODUTO, PRECO, CATEGORIA_ID, FORNECEDOR_ID)
	VALUES ('História em Quadrinhos - Editora Panini Comics', 'Crise nas Infinitas Terras', 110.00, 1, 1);

	-- CLIENTES
	
	INSERT INTO CLIENTE (CPF, DATA_NASCIMENTO, EMAIL, NOME, RG, TELEFONE, ENDERECO_ID)
	VALUES ('088.559.289-83', '1997-01-25', 'nataliacmsa@gmail.com', 'Natália Cristina Martins de Sá', '9.778.621-25', '(43)99190-5205', 2);
	
	-- VENDAS
	
	INSERT INTO venda (aprovacao, data_compra, quantidade_de_itens, situacao)
	VALUES ('APROVADA', '2018-05-03', 1, 'FECHADA');

	-- CLIENTES VENDA
	
	INSERT INTO cliente_venda VALUES (1, 2);

	-- PRODUTOS VENDA
	
	INSERT INTO produto_venda VALUES (1, 1);


	
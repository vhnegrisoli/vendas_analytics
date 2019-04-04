-- VIEWS PARA OS RELATÓRIOS DA DASHBOARD

-- GRÁFICO PRINCIPAL

CREATE VIEW VENDAS_POR_PERIODO AS
SELECT 
	ROW_NUMBER() OVER (ORDER BY v.data_compra) AS "id",
	COUNT(v.id) AS "quantidade_de_vendas",  
	SUM(p.preco) AS "lucro_total", 
	DATENAME(MONTH, v.data_compra) AS "meses"
FROM VENDA v 
INNER JOIN produto_venda pv ON v.id = pv.venda_id
INNER JOIN produto p ON p.id = pv.produto_id
GROUP BY v.data_compra;

-- CARDS INICIAIS
CREATE VIEW VENDAS_POR_CLIENTE AS
SELECT 
	ROW_NUMBER() OVER (ORDER BY v.mes_compra) AS "id",
	COUNT(c.ID) AS "Clientes",
	v.mes_compra AS "Meses"
FROM Cliente c
INNER JOIN Venda v ON c.id = v.cliente_id
GROUP BY v.mes_compra;

CREATE VIEW VENDAS_POR_PRODUTO AS
SELECT 
	ROW_NUMBER() OVER (ORDER BY v.data_compra) AS "id",
	COUNT(p.ID) AS "Produtos",
	DATENAME(MONTH, v.data_compra) AS "Meses"
FROM Produto p
INNER JOIN produto_venda pv ON p.id = pv.produto_id
INNER JOIN Venda v ON v.id = pv.venda_id
GROUP BY v.data_compra;

CREATE VIEW VENDAS_CONCRETIZADAS AS
SELECT 
	ROW_NUMBER() OVER (ORDER BY data_compra) AS "id",
	COUNT(ID) AS "vendas_concluidas",
	DATENAME(MONTH, data_compra) AS "meses"
FROM VENDA
WHERE SITUACAO = 'FECHADA' AND APROVACAO = 'APROVADA'
GROUP BY data_compra;

CREATE VIEW VENDAS_NAO_CONCRETIZADAS AS
SELECT 
	ROW_NUMBER() OVER (ORDER BY data_compra) AS "id",
	COUNT(ID) AS "vendas_concluidas",
	DATENAME(MONTH, data_compra) AS "meses"
FROM VENDA
WHERE APROVACAO != 'APROVADA'
GROUP BY data_compra;

-- CONSULTA EXPORTAR CSV

CREATE VIEW EXPORTAR_CSV AS
SELECT
		c.NOME				AS "Nome Cliente", 
		c.CPF				AS "CPF Cliente",
		c.EMAIL				AS "Email Cliente",
		CONCAT(e.RUA
		, ', nº ', e.NUMERO)AS "Endereço Cliente",
		cd.CIDADE			AS "Cidade",
		es.ESTADO			AS "Estado",
		r.NOME				AS "Região",
		u.NOME				AS "Usuário Cliente",
		v.ID				AS "Código Venda",
		v.QUANTIDADE_DE_ITENS
							AS "Quantidade Itens",
		v.DATA_COMPRA		AS "Data Venda",
		v.SITUACAO			AS "Situação Venda",
		v.aprovacao			AS "Aprovação Venda",
		p.ID				AS "Código Produto",
		p.NOME_PRODUTO		AS "Produto",
		p.PRECO				AS "Valor Pedido",
		ct.DESCRICAO		AS "Categoria",
		f.CNPJ				AS "CNPJ Fornecedor",
		f.NOME_FANTASIA		AS "Fornecedor Nome Fantasia",
		f.RAZAO_SOCIAL		AS "Razão Social Fornecedor"
FROM Cliente c
	LEFT JOIN Usuario u ON c.id = u.cliente_id
	LEFT JOIN Endereco e ON e.id = c.endereco_id
	LEFT JOIN Cidade cd ON cd.id = e.cidade_id
	LEFT JOIN Estado es ON es.id = e.estado_id
	LEFT JOIN Regiao r ON es.regiao_id = r.id
	LEFT JOIN Venda v ON v.cliente_id = c.id
	LEFT JOIN Produto_Venda pv ON pv.venda_id = v.id
	LEFT JOIN Produto p ON p.id = pv.produto_id
	LEFT JOIN Categoria ct ON ct.id = p.fornecedor_id
	LEFT JOIN Fornecedor f ON f.id = p.fornecedor_id;
	
-- ANALYTICS


-- HISTORICO DE VENDAS

CREATE VIEW HISTORICO_DE_VENDA AS
SELECT
	ROW_NUMBER() OVER (ORDER BY data_compra) AS "id",
	v.ID			AS "codigo_venda",
	v.SITUACAO		AS "situacao_venda",
	v.APROVACAO		AS "aprovacao_venda",
	pv.quantidade	AS "quantidade_itens",
	c.nome			AS "nome_cliente",
	c.email			AS "email_cliente",
	CONCAT(c.rua
    ,', nº',c.numero) AS "endereco_cliente",
	CONCAT(c.cidade,
	' - ', e.estado) AS "local_cliente",
	p.nome_produto	AS "nome_produto",
	p.descricao		AS "descricao_produto",
	p.preco			AS "preco"
FROM Cliente c
	INNER JOIN Estado e ON e.id = c.estado_id
	INNER JOIN Regiao r ON e.regiao_id = r.id
	INNER JOIN Venda v ON v.cliente_id = c.id
	INNER JOIN Produto_Venda pv ON pv.venda_id = v.id
	INNER JOIN Produto p ON p.id = pv.produto_id
	INNER JOIN Categoria ct ON ct.id = p.fornecedor_id
	INNER JOIN Fornecedor f ON f.id = p.fornecedor_id

-- ANALYTICS: LUCRO E MEDIA POR PRODUTO

CREATE VIEW LUCRO_MEDIA_PRODUTO AS
    SELECT  ROW_NUMBER() OVER (ORDER BY p.nome_produto) AS "id",
    	p.nome_produto as "produto",
    	SUM(p.PRECO * pv.quantidade) as "quantidade",
    	CAST(AVG(p.PRECO * pv.QUANTIDADE) as NUMERIC(10,2)) as "media"
    FROM Produto p INNER JOIN produto_venda pv ON p.id = pv.produto_id
    GROUP BY p.nome_produto

-- ANALYTICS: VENDAS POR REGIÕES
CREATE VIEW VENDAS_POR_REGIOES AS
SELECT  COUNT(v.id) as "qtdVendas",
		SUM(p.preco * pv.quantidade) as "lucro",
		AVG(p.preco * pv.quantidade)  as "media",
		SUM(pv.quantidade)	as "qtdProdutos",
		COUNT(c.ID) as "qtdClientes",
		r.nome as "regiao",
		e.estado as "estado"
FROM Regiao r
INNER JOIN estado e ON r.id = e.regiao_id
INNER JOIN cliente c ON c.estado_id = e.id
INNER JOIN venda v ON v.cliente_id = c.id
INNER JOIN produto_venda pv ON pv.venda_id = v.id
INNER JOIN produto p ON p.id = pv.produto_id
GROUP BY r.nome, e.estado;
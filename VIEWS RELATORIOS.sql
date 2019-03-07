-- VIEWS PARA OS RELATÓRIOS DO ANALYTICS
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
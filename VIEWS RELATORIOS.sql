-- VIEWS PARA OS RELATÓRIOS DO ANALYTICS

CREATE VIEW VENDAS_POR_PERIODO AS
SELECT 
	COUNT(v.id) AS "Quantidade de Vendas",  
	SUM(p.preco) AS "Lucro total", 
	DATENAME(MONTH, v.data_compra) AS "Meses"
FROM VENDA v 
INNER JOIN produto_venda pv ON v.id = pv.venda_id
INNER JOIN produto p ON p.id = pv.produto_id
GROUP BY v.data_compra;

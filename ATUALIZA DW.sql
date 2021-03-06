-- ATUALIZAR O DATA WAREHOUSE

DROP TABLE FACT_VENDAS;

TRUNCATE TABLE DIM_CLIENTE;
TRUNCATE TABLE DIM_PRODUTO;
TRUNCATE TABLE DIM_REGIAO;
TRUNCATE TABLE DIM_VENDAS;

--ATUALIZA DIMENSÃO CLIENTE

INSERT DIM_CLIENTE (DIM_CLIENTE_ID, DIM_CLIENTE_NOME, DIM_CLIENTE_CIDADE)
SELECT DISTINCT ID, NOME, CIDADE FROM CLIENTE;

-- ATUALIZA REGIÃO

INSERT DIM_REGIAO (DIM_REGIAO_ID, DIM_REGIAO_ESTADO, DIM_REGIAO_SIGLA, DIM_REGIAO_NOME)
SELECT e.ID, e.ESTADO, e.SIGLA, r.NOME
FROM ESTADO e LEFT JOIN REGIAO r ON e.regiao_id = r.id;

-- DIMENSÃO PRODUTO

INSERT INTO DIM_PRODUTO (DIM_PRODUTO_ID, DIM_PRODUTO_NOME, DIM_PRODUTO_CATEGORIA, DIM_PRODUTO_FORNECEDOR)
SELECT p.ID, p.NOME_PRODUTO, c.DESCRICAO, f.RAZAO_SOCIAL
FROM Produto p LEFT JOIN Categoria c ON c.id = p.categoria_id
               LEFT JOIN Fornecedor f ON p.fornecedor_id = f.id;

-- DIMENSÃO VENDAS

INSERT INTO DIM_VENDAS (DIM_VENDAS_ID, DIM_VENDAS_SITUACAO, DIM_VENDAS_APROVACAO)
SELECT ID, SITUACAO, APROVACAO FROM VENDA;

-- FATOS VENDAS

CREATE TABLE FACT_VENDAS (
	DIM_ID_VENDAS					INT,
	DIM_ID_CLIENTE					INT,
	DIM_ID_REGIAO					INT,
	DIM_ID_PRODUTO					INT,
	FACT_PRECO						NUMERIC(10,2),
	FACT_QUANTIDADE_ITENS			NUMERIC(10),
	FACT_QUANTIDADE_VENDAS			NUMERIC(10),
	FACT_DATA_COMPRA				DATETIME,
	FACT_ANO_COMPRA					NUMERIC(4),
	FACT_MES_COMPRA					NUMERIC(2),
	FACT_DIA_COMPRA					NUMERIC(2),
	FACT_MES_NOME_COMPRA			VARCHAR(15),
	FACT_DIA_NOME_COMPRA			VARCHAR(25),
	FACT_HORA_COMPRA				NUMERIC(2),
	FACT_MINUTO_COMPRA				NUMERIC(2),
	FACT_SEGUNDO_COMPRA				NUMERIC(2),
	FOREIGN KEY (DIM_ID_VENDAS)
	REFERENCES DIM_VENDAS (DIM_VENDAS_ID),
	FOREIGN KEY (DIM_ID_CLIENTE)
	REFERENCES DIM_CLIENTE (DIM_CLIENTE_ID),
	FOREIGN KEY (DIM_ID_REGIAO)
	REFERENCES DIM_REGIAO (DIM_REGIAO_ID),
	FOREIGN KEY (DIM_ID_PRODUTO)
	REFERENCES DIM_PRODUTO (DIM_PRODUTO_ID)
);

INSERT INTO FACT_VENDAS
(DIM_ID_VENDAS, DIM_ID_CLIENTE, DIM_ID_REGIAO, DIM_ID_PRODUTO,
FACT_PRECO, FACT_QUANTIDADE_ITENS, FACT_QUANTIDADE_VENDAS, FACT_DATA_COMPRA,
FACT_ANO_COMPRA, FACT_MES_COMPRA, FACT_DIA_COMPRA, FACT_MES_NOME_COMPRA,
FACT_DIA_NOME_COMPRA, FACT_HORA_COMPRA, FACT_MINUTO_COMPRA, FACT_SEGUNDO_COMPRA)
SELECT
		v.ID,
		c.ID,
		e.ID,
		p.ID,
		SUM(p.PRECO),
		SUM(pv.QUANTIDADE),
		COUNT(v.ID),
		v.DATA_COMPRA,
		YEAR(v.DATA_COMPRA),
		MONTH(v.DATA_COMPRA),
		DAY(v.DATA_COMPRA),
		DATENAME(MONTH, v.DATA_COMPRA),
		DATENAME(DW, v.DATA_COMPRA),
		DATEPART(HOUR, v.DATA_COMPRA),
		DATEPART(MINUTE, v.DATA_COMPRA),
		DATEPART(SECOND, v.DATA_COMPRA)
FROM VENDA v
LEFT JOIN produto_venda pv ON v.ID = pv.venda_id
LEFT JOIN produto p ON p.id = pv.produto_id
LEFT JOIN cliente c ON c.id = v.cliente_id
LEFT JOIN estado e ON e.id = c.estado_id
GROUP BY v.ID, p.ID, c.ID, e.ID, v.data_compra;
CREATE TABLE pessoa(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome  VARCHAR(50) NOT NULL,
	ativo boolean,
	logradouro VARCHAR(50),
	numero VARCHAR(50),
	complemento VARCHAR(50),
	bairro VARCHAR(50),
	cep VARCHAR(50),
	cidade VARCHAR(50),
	estado VARCHAR(50)
)engine=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo, logradouro, numero,complemento, bairro, cep, cidade, estado) VALUES ('Thiago', true, 'Rua Engenheiro Villares da Silva', '1190','Fundos', 'Itaquera', '0253520', 'São Paulo', 'São Paulo');

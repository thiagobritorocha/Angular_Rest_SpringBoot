create table lancamento(
	codigo bigint(20) primary key auto_increment,
    descricao varchar(50) not null,
    data_vencimento date not null,
    data_pagamento date,
    valor decimal(10,2) not null,
    observacao varchar(100),
    tipo varchar(20) not null,
    codigo_categoria bigint(20) not null,
    codigo_pessoa bigint(20) not null,
    foreign key (codigo_categoria) references categoria(codigo),
    foreign key (codigo_pessoa) references pessoa(codigo)
    )engine=InnoDB default charset=utf8;
    
    insert into lancamento 
    (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa)
    values
    ('teste','2018-05-04','2018-05-04',1.0,'teste observac', 'RECEITA',1,1)
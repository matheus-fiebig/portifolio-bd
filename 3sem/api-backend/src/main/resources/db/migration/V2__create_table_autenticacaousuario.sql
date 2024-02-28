create table autenticacao_usuario (
	id int not null AUTO_INCREMENT,
	login varchar(100) not null unique,
	senha varchar(300) not null,
	fl_primeiro_acesso bit DEFAULT 1,
	
	primary key(id)
);
create table usuarios(
    id bigint not null IDENTITY(1,1) primary KEY,
    nome varchar(100) not null,
    email varchar(255) not null unique,
);
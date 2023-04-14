create table usuarios(
    id bigint not null IDENTITY(1,1) primary KEY,
    login varchar(100) not null,
    senha varchar(255) not null unique,
);
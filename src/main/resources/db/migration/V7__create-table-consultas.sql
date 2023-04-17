create table consultas(

    id bigint not null IDENTITY(1,1) primary KEY,
    medico_id bigint not null,
    paciente_id bigint not null,
    data datetime not null,

    constraint fk_consultas_medico_id foreign key(medico_id) references medicos(id),
    constraint fk_consultas_paciente_id foreign key(paciente_id) references pacientes(id)
); 
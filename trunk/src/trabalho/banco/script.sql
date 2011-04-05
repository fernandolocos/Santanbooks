CREATE TABLE cadastro(
ra int PRIMARY KEY,
nome varchar(255),
curso varchar(255)
)

insert into cadastro values (095789,'Joao','Computacao');
insert into cadastro values (123123,'Jose','Matematica');
insert into cadastro values (233123,'Astolfo','Engenharia');

SELECT * FROM cadastro;

delete from cadastro where nome = 'Joao';
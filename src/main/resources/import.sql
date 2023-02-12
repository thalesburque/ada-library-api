insert into publisher (name, description) values ('Artmed','livros técnicos');
insert into publisher (name, description) values ('Abril','livros gerais');

insert into genre (name) values ('Educacao');
insert into genre (name) values ('Drama');

insert into book (isbn, name, genre_id, publisher_id) values ('1', 'Java Web', 1, 1);
insert into book (isbn, name, genre_id, publisher_id) values ('2', 'Metamorfose', 2, 2);
insert into book (isbn, name, genre_id, publisher_id) values ('teste', 'O velho e o mar', 2, 2);
insert into book (isbn, name, genre_id, publisher_id) values ('asdf', 'O mítico homem-mês', 1, 1);




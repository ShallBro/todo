create table todo.user (
                           id serial primary key not null,
                           name varchar(250) not null
);

create table todo.status (
                             id serial primary key not null,
                             name varchar(250)
);

create table todo.task (
                           id serial primary key not null,
                           user_id integer references todo.user,
                           name varchar(250),
                           description varchar,
                           deadline timestamp,
                           status_id integer references todo.status
);

insert into todo."user" (name) values ('Artem');
insert into todo."user" (name) values ('Vova');
insert into todo."user" (name) values ('German');
insert into todo.status (name) values ('todo');
insert into todo.status (name) values ('in progress');
insert into todo.status (name) values ('done');

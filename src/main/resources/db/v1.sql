create table users
(
    id       bigserial,
    username varchar(30) not null unique,
    password varchar(80) not null,
    email    varchar(256) unique,
    primary key (id)
);

create table role
(
    id   serial,
    name varchar(50) not null,
    primary key (id)
);

create table user_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references role (id)
);

insert into role(name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users(username, password, email)
values ('user', '$2a$10$tecGLPkUjM/L4RA/o2Os/.FOwaB1AtGV.kx.iIBgAAhD98aXvozE.', 'user@mail.ru'),
       ('admin', '$2a$10$cgSbX85roS0AjBDJzu3eZuFHnhCmlcgqGTPVz.TLG2eY.7DmWJUqK', 'admin@mail.ru');

insert into user_roles(user_id, role_id)
values (1, 1),
       (2, 2);
create table if not exists media (
    id int auto_increment primary key,
    name varchar unique not null,
    url varchar not null,
    created timestamp not null
);

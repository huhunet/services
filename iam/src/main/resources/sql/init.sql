create table user(
    id integer primary key,
    description varchar(255) comment '描述',
    name varchar(255) not null comment '用户名',
    password varchar(255) not null,
    role_id integer not null,
    create_time timestamp not null,
    update_time timestamp not null
);

create table role(
     id integer primary key,
     description varchar(255),
     name varchar(255),
     role_id integer not null,
     permission_ids varchar(255) not null,
     create_time timestamp not null,
     update_time timestamp not null
);

create table permisseion(
     id integer primary key,
     description varchar(255),
     name varchar(255),
     permission_id integer not null,
     create_time timestamp not null,
     update_time timestamp not null
);
create table if not exists "xdisx-customer".customers (
    "id" numeric(10) primary key,
    "customer_type" varchar(32) not null ,
    "created" timestamp not null ,
    "modified" timestamp not null ,
    "version" bigint not null
    );

CREATE SEQUENCE customer_id_seq START 1;
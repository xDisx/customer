alter table "xdisx-customer".customers
drop column if exists customer_type;

alter table "xdisx-customer".customers
    add column if not exists first_name varchar(64) not null default 'Ion',
    add column if not exists last_name varchar(64) not null  default 'Pop',
    add column if not exists email varchar(64) not null default 'ion.pop@yahoo.com',
    add column if not exists phone_number varchar(64) not null default '+07434556454',
    add column if not exists address varchar(256) not null default 'Fagului 26';
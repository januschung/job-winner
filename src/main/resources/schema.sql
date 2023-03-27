create table job_application(
    id serial primary key,
    company_name varchar(50),
    job_title varchar(50),
    salary_range varchar(30),
    job_url varchar(200),
    applied_date date,
    description varchar(500),
    status varchar(20)
);

create table profile(
    id serial primary key,
    first_name varchar(200),
    last_name varchar(200),
    address_street_1 varchar(200),
    address_street_2 varchar(200),
    address_city varchar(50),
    address_state varchar(50),
    address_zip varchar(20),
    linkedin varchar(200),
    github varchar(200),
    personal_website varchar(200)
);

insert into profile(id)
    values(1);
create table if not exists job_application(
    id serial primary key,
    company_name varchar(50),
    job_title varchar(50),
    salary_range varchar(30),
    job_url varchar(200),
    applied_date date,
    description varchar(500),
    status varchar(20)
);

create table if not exists profile(
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

create table if not exists interview (
    id serial primary key,
    job_application_id int not null,
    interview_date date not null,
    interviewer varchar(255),
    description varchar(500),
    status varchar(20),
    foreign key (job_application_id) references job_application(id) on delete cascade
);

create table if not exists offer (
    id serial primary key,
    job_application_id int not null,
    offer_date date not null,
    salary_offered varchar(255),
    description varchar(500),
    foreign key (job_application_id) references job_application(id) on delete cascade,
    constraint job_application_unique unique (job_application_id)
);

insert into profile(id)
    values(1);

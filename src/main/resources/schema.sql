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

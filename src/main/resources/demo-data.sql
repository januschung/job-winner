-- Sample data for the `profile` table
UPDATE profile
SET
    first_name = 'John',
    last_name = 'Doe',
    address_street_1 = '123 Main St',
    address_street_2 = 'Apt 4B',
    address_city = 'San Francisco',
    address_state = 'CA',
    address_zip = '94105',
    linkedin = 'https://linkedin.com/in/johndoe',
    github = 'https://github.com/johndoe',
    personal_website = 'https://johndoe.com'
WHERE id = 1;

-- Sample job applications data
INSERT INTO job_application (
    company_name,
    job_title,
    salary_range,
    job_url,
    applied_date,
    description,
    status
)
VALUES
('Acme Corp', 'Software Engineer', '100k-120k', 'https://www.acme.com/jobs/software-engineer', CURRENT_DATE + 10, 'Develop software solutions', 'open'),
('BetaTech', 'Data Scientist', '90k-110k', 'https://www.betatech.com/jobs/data-scientist', CURRENT_DATE + 11, 'Analyze data and provide insights', 'open'),
('Gamma Solutions', 'Product Manager', '120k-140k', 'https://www.gammasolutions.com/jobs/product-manager', CURRENT_DATE + 12, 'Lead product development', 'active'),
('Delta Inc.', 'Web Developer', '80k-100k', 'https://www.deltainc.com/jobs/web-developer', CURRENT_DATE + 13, 'Build responsive websites', 'active'),
('Epsilon LLC', 'HR Specialist', '60k-80k', 'https://www.epsilonllc.com/jobs/hr-specialist', CURRENT_DATE + 14, 'Manage employee relations', 'open'),
('Zeta Group', 'Software Engineer', '95k-115k', 'https://www.zetagroup.com/jobs/software-engineer', CURRENT_DATE + 15, 'Write clean, maintainable code', 'active'),
('Omega Technologies', 'DevOps Engineer', '105k-125k', 'https://www.omegatech.com/jobs/devops-engineer', CURRENT_DATE + 10, 'Automate infrastructure deployment', 'active'),
('Lambda Solutions', 'Business Analyst', '85k-105k', 'https://www.lambdasolutions.com/jobs/business-analyst', CURRENT_DATE + 11, 'Gather and analyze business requirements', 'active'),
('NuTech', 'Data Analyst', '70k-90k', 'https://www.nutech.com/jobs/data-analyst', CURRENT_DATE + 12, 'Create reports and dashboards', 'open'),
('Sigma Enterprises', 'Sales Manager', '100k-120k', 'https://www.sigmaenterprises.com/jobs/sales-manager', CURRENT_DATE + 13, 'Manage sales teams and strategy', 'open'),
('OmegaTech', 'Front-end Developer', '90k-110k', 'https://www.omegatech.com/jobs/front-end-developer', CURRENT_DATE + 14, 'Develop front-end features', 'open'),
('XenoTech', 'Back-end Developer', '100k-120k', 'https://www.xenotech.com/jobs/back-end-developer', CURRENT_DATE + 15, 'Work on back-end server systems', 'rejected');

-- Sample interviews data (linked to job applications)
INSERT INTO interview (
    job_application_id,
    interview_date,
    interviewer,
    description,
    status
)
VALUES
(1, CURRENT_DATE + 20, 'Alice Johnson', 'Technical interview with the development team', 'open'),
(2, CURRENT_DATE + 21, 'Bob Brown', 'Data analysis task and discussion', 'open'),
(3, CURRENT_DATE + 22, 'Charlie Taylor', 'Product management interview', 'open'),
(4, CURRENT_DATE + 23, 'David Green', 'Web development skills test', 'open'),
(5, CURRENT_DATE + 24, 'Eva White', 'HR interview', 'open'),
(6, CURRENT_DATE + 25, 'Frank Black', 'Coding interview', 'open'),
(7, CURRENT_DATE + 20, 'Grace Blue', 'DevOps and infrastructure test', 'open'),
(8, CURRENT_DATE + 21, 'Hannah Red', 'Business analysis and scenario discussion', 'open');

-- Sample offers data (linked to job applications)
INSERT INTO offer (
    job_application_id,
    offer_date,
    salary_offered,
    description
)
VALUES
(1, CURRENT_DATE + 30, '110k', 'Offer extended for Software Engineer at Acme Corp'),
(2, CURRENT_DATE + 31, '105k', 'Offer extended for Data Scientist at BetaTech'),
(6, CURRENT_DATE + 32, '115k', 'Offer extended for Software Engineer at Zeta Group');

-- Sample frequent url data
INSERT INTO frequent_url (
    title,
    url
)
VALUES
('LinkedIn Jobs', 'https://www.linkedin.com/jobs/'),
('Indeed', 'https://www.indeed.com/'),
('Glassdoor', 'https://www.glassdoor.com/Job/'),
('Monster', 'https://www.monster.com/jobs/'),
('We Work Remotely', 'https://weworkremotely.com/'),
('AngelList', 'https://angel.co/jobs'),
('Remotive', 'https://remotive.io/remote-jobs'),
('HackerRank Jobs', 'https://www.hackerrank.com/jobs'),
('Hired', 'https://hired.com/'),
('Stack Overflow Jobs', 'https://stackoverflow.com/jobs'),
('FlexJobs', 'https://www.flexjobs.com/'),
('Wellfound (AngelList Talent)', 'https://angel.co/talent'),
('Turing', 'https://www.turing.com/jobs/'),
('Dice', 'https://www.dice.com/'),
('Ladders', 'https://www.theladders.com/'),
('Outsourcely', 'https://www.outsourcely.com/'),
('Europe Remotely', 'https://europeremotely.com/'),
('Jobspresso', 'https://jobspresso.co/'),
('Remote OK', 'https://remoteok.io/'),
('Just Remote', 'https://justremote.co/');

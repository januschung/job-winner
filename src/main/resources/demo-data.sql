-- Sample data for the `profile` table
UPDATE profile
SET
    first_name = 'Janus',
    last_name = 'Chung',
    address_street_1 = '123 Main St',
    address_street_2 = 'Apt 4',
    address_city = 'City',
    address_state = 'State',
    address_zip = '12345',
    linkedin = 'https://linkedin.com/in/janus',
    github = 'https://github.com/januschung',
    personal_website = 'https://januschung.github.io',
    email = 'test@test.com',
    telephone = '123-456-7890'
WHERE id = 1;

-- Sample job applications data
INSERT INTO job_application (
    company_name,
    job_title,
    salary_range,
    job_url,
    applied_date,
    description,
    note,
    status
)
VALUES
('Google', 'Software Engineer', '180k-220k', 'https://careers.google.com/jobs/se', CURRENT_DATE + 1, 'Develop scalable web applications', 'Great Glassdoor reviews', 'open'),
('Microsoft', 'AI Engineer', '170k-210k', 'https://careers.microsoft.com/jobs/ai', CURRENT_DATE + 2, 'Build machine learning models', 'Still in early stages', 'open'),
('Amazon', 'Cloud Architect', '180k-220k', 'https://www.amazon.jobs/jobs/cloud-arch', CURRENT_DATE + 3, 'Architect scalable cloud systems', 'AWS experience preferred', 'open'),
('Meta', 'Test Engineer', '150k-180k', 'https://www.metacareers.com/jobs/test', CURRENT_DATE + 4, 'Test automation frameworks', 'Sent follow-up email', 'open'),
('Apple', 'DevOps Engineer', '160k-190k', 'https://jobs.apple.com/jobs/devops', CURRENT_DATE + 5, 'Maintain CI/CD pipelines', 'Waiting on recruiter reply', 'open'),
('Netflix', 'Product Designer', '150k-180k', 'https://jobs.netflix.com/jobs/designer', CURRENT_DATE + 6, 'Design user experiences', 'Excited about their streaming platform', 'open'),
('Adobe', 'UX Designer', '140k-170k', 'https://careers.adobe.com/jobs/ux', CURRENT_DATE + 7, 'Improve UI flow for creative tools', 'Portfolio shared', 'open'),
('Salesforce', 'Network Engineer', '150k-180k', 'https://www.salesforce.com/careers/jobs/neteng', CURRENT_DATE + 8, 'Design network infrastructure', 'Waiting on update', 'open'),
('Oracle', 'Data Engineer', '140k-170k', 'https://www.oracle.com/careers/jobs/data-eng', CURRENT_DATE + 9, 'ETL pipelines for analytics', 'Pinged recruiter on LinkedIn', 'open'),
('IBM', 'Front-end Developer', '130k-160k', 'https://www.ibm.com/careers/jobs/frontend', CURRENT_DATE + 10, 'Build React-based dashboard', 'Enterprise focus', 'open'),
('Intel', 'Backend Developer', '140k-170k', 'https://www.intel.com/careers/jobs/backend', CURRENT_DATE + 11, 'Secure RESTful APIs', 'Pending code challenge', 'open'),
('NVIDIA', 'Mobile Developer', '160k-190k', 'https://www.nvidia.com/careers/jobs/mobile', CURRENT_DATE + 12, 'Work on mobile applications', 'Great tech stack', 'open'),
('Stripe', 'Product Manager', '180k-220k', 'https://stripe.com/jobs/pm', CURRENT_DATE + 13, 'Manage product roadmap', 'Met PM at meetup', 'active'),
('Airbnb', 'SRE', '180k-210k', 'https://careers.airbnb.com/jobs/sre', CURRENT_DATE + 14, 'Uptime focus', 'Interviewed last week', 'active'),
('Uber', 'Security Engineer', '170k-200k', 'https://www.uber.com/careers/jobs/seceng', CURRENT_DATE + 15, 'Pen testing', 'Final round soon', 'active'),
('Tesla', 'Fullstack Dev', '150k-180k', 'https://www.tesla.com/careers/jobs/fullstack', CURRENT_DATE + 16, 'End-to-end features', 'Tech test done', 'active'),
('Spotify', 'Backend Engineer', '160k-190k', 'https://www.spotifyjobs.com/jobs/server', CURRENT_DATE + 17, 'Music streaming infrastructure', 'Awaiting next round', 'active'),
('Twitter', 'QA Analyst', '120k-150k', 'https://careers.twitter.com/jobs/qa', CURRENT_DATE + 18, 'Test regression cases', 'Rejected with feedback', 'rejected'),
('LinkedIn', 'DevRel', '140k-170k', 'https://www.linkedin.com/careers/jobs/devrel', CURRENT_DATE + 19, 'Engage developer community', 'Ghosted after recruiter chat', 'ghosted'),
('GitHub', 'ML Ops', '170k-200k', 'https://github.com/careers/jobs/mlops', CURRENT_DATE + 20, 'Maintain models in prod', 'No updates post final round', 'ghosted'),
('Atlassian', 'Platform Engineer', '150k-180k', 'https://www.atlassian.com/careers/jobs/platform', CURRENT_DATE + 21, 'Automate development workflows', 'Ghosted after take-home', 'ghosted'),
('Shopify', 'Infrastructure Engineer', '180k-210k', 'https://www.shopify.com/careers/jobs/infra', CURRENT_DATE + 22, 'Modernize infrastructure', 'Rejection after panel', 'rejected'),
('Square', 'Support Engineer', '120k-145k', 'https://squareup.com/careers/jobs/support', CURRENT_DATE + 23, 'Tech support workflows', 'Quick rejection', 'rejected'),
('Palantir', 'Software Engineer', '180k-220k', 'https://www.palantir.com/careers/jobs/engineer', CURRENT_DATE + 24, 'Data platform development', 'No reply after HR call', 'ghosted'),
('Databricks', 'Tooling Engineer', '160k-190k', 'https://www.databricks.com/careers/jobs/tooling', CURRENT_DATE + 25, 'Dev tools for data engineers', 'Ghosted after tech screen', 'ghosted');

-- Sample interviews data (linked to job applications)
INSERT INTO interview (
    job_application_id,
    interview_date,
    interviewer,
    description,
    status
)
VALUES
(1, CURRENT_DATE + 5, 'Sarah Chen', 'System design deep-dive', 'open'),
(2, CURRENT_DATE + 6, 'Michael Park', 'ML architecture discussion', 'open'),
(3, CURRENT_DATE + 7, 'David Kim', 'Cloud deployment design', 'open'),
(4, CURRENT_DATE + 8, 'Jennifer Martinez', 'Automated testing walk-through', 'open'),
(5, CURRENT_DATE + 9, 'Robert Johnson', 'CI/CD system review', 'open'),
(6, CURRENT_DATE + 10, 'Emily Davis', 'Design portfolio review', 'open'),
(7, CURRENT_DATE + 11, 'James Wilson', 'UX case study', 'open'),
(8, CURRENT_DATE + 12, 'Andrew Rodriguez', 'Networking concepts', 'open'),
(13, CURRENT_DATE + 13, 'Jessica Lee', 'Product case presentation', 'open'),
(14, CURRENT_DATE + 14, 'Thomas Anderson', 'On-call rotation Q&A', 'open'),
(15, CURRENT_DATE + 15, 'Maria Garcia', 'Security policy overview', 'open'),
(16, CURRENT_DATE + 16, 'Christopher Brown', 'Fullstack architecture deep dive', 'open');


-- Sample offers data (linked to job applications)
INSERT INTO offer (
    job_application_id,
    offer_date,
    salary_offered,
    description
)
VALUES
(1, CURRENT_DATE + 20, '200k', 'Offer from Google for Software Engineer'),
(2, CURRENT_DATE + 21, '190k', 'Offer from Microsoft for AI Engineer'),
(6, CURRENT_DATE + 22, '165k', 'Offer from Netflix for Product Designer'),
(14, CURRENT_DATE + 23, '195k', 'Offer from Airbnb for SRE'),
(15, CURRENT_DATE + 24, '185k', 'Offer from Uber for Security Engineer');

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
('Wellfound', 'https://angel.co/talent'),
('Turing', 'https://www.turing.com/jobs/'),
('Dice', 'https://www.dice.com/'),
('Ladders', 'https://www.theladders.com/'),
('Outsourcely', 'https://www.outsourcely.com/'),
('Europe Remotely', 'https://europeremotely.com/'),
('Jobspresso', 'https://jobspresso.co/'),
('Remote OK', 'https://remoteok.io/'),
('Just Remote', 'https://justremote.co/');

-- Sample question data
INSERT INTO question (question, answer) VALUES
('Can you tell me about yourself?',
 'I am a software developer with a strong background in full-stack development. I have experience working with React, Node.js, and databases like PostgreSQL. I enjoy solving complex problems and collaborating with teams to build scalable applications.'),
('What are your strengths and weaknesses?',
 'One of my strengths is problem-solving. I enjoy breaking down complex challenges and finding efficient solutions. I am also a quick learner, always eager to adapt to new technologies. A weakness I am working on is delegating tasks more effectively, as I sometimes take on too much responsibility myself.'),
('Describe a challenging project you worked on.',
 'I worked on a project that involved migrating a legacy application to a modern stack. The biggest challenge was ensuring minimal downtime while transferring a large database. I led the effort in designing a phased rollout and automated testing to ensure a smooth transition.'),
('How do you handle tight deadlines and pressure?',
 'I prioritize tasks by breaking them into smaller steps and focusing on high-impact activities first. I also communicate proactively with my team to ensure everyone is aligned. When under pressure, I stay calm, rely on my problem-solving skills, and adapt to changing priorities.'),
('Why do you want to work at our company?',
 'I admire your company’s innovation in the tech industry and its culture of continuous learning. I am particularly excited about the opportunity to work on cutting-edge projects with a talented team, where I can contribute my skills and grow professionally.');

-- Sample data for the `profile` table
UPDATE profile
SET
    first_name = 'Samus',
    last_name = 'Aran',
    address_street_1 = '7 Zebes Way',
    address_street_2 = 'Unit Chozo',
    address_city = 'Brinstar',
    address_state = 'SR',
    address_zip = '99999',
    linkedin = 'https://linkedin.com/in/samusaran',
    github = 'https://github.com/zero-suit-dev',
    personal_website = 'https://samus.dev',
    email = 'samus@galacticfed.io',
    telephone = '800-ZERO-DRM'
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
('Hyrule Systems', 'Software Engineer', '110k-130k', 'https://hyrulesys.com/jobs/se', CURRENT_DATE + 1, 'Develop combat mechanics engine', 'Great Glassdoor reviews', 'open'),
('NekoTech', 'AI Engineer', '120k-150k', 'https://nekotech.ai/jobs/ai', CURRENT_DATE + 2, 'Build NPC behavior models', 'Still in early stages', 'open'),
('KoopaCloud', 'Cloud Architect', '140k-160k', 'https://koopacloud.io/jobs/cloud-arch', CURRENT_DATE + 3, 'Architect scalable cloud systems', 'Love the Mario branding', 'open'),
('Aperture Logic', 'Test Engineer', '90k-110k', 'https://aperturelogic.com/jobs/test', CURRENT_DATE + 4, 'Test automated turrets', 'Sent follow-up email', 'open'),
('Midgar Works', 'DevOps Engineer', '105k-125k', 'https://midgarworks.com/jobs/devops', CURRENT_DATE + 5, 'Maintain CI/CD pipelines', 'Waiting on recruiter reply', 'open'),
('Zanarkand Dynamics', 'Game Designer', '85k-105k', 'https://zanarkanddyn.com/jobs/designer', CURRENT_DATE + 6, 'Design game levels', 'Excited about their new RPG', 'open'),
('EchoSlay', 'UX Designer', '95k-115k', 'https://echoslay.com/jobs/ux', CURRENT_DATE + 7, 'Improve UI flow for AR battles', 'Portfolio shared', 'open'),
('RaptureNet', 'Network Engineer', '100k-120k', 'https://rapturenet.com/jobs/neteng', CURRENT_DATE + 8, 'Design underwater comms', 'Waiting on update', 'open'),
('MoogleSoft', 'Data Engineer', '100k-125k', 'https://mooglesoft.com/jobs/data-eng', CURRENT_DATE + 9, 'ETL pipelines for player stats', 'Pinged recruiter on LinkedIn', 'open'),
('WumpaWorks', 'Front-end Developer', '90k-110k', 'https://wumpaworks.io/jobs/frontend', CURRENT_DATE + 10, 'Build React-based dashboard', 'Small but growing studio', 'open'),
('VaultTec Labs', 'Backend Developer', '110k-130k', 'https://vaulttecjobs.com/backend', CURRENT_DATE + 11, 'Secure RESTful APIs', 'Pending code challenge', 'open'),
('BoomerangX', 'Mobile Developer', '100k-115k', 'https://boomerangx.com/jobs/mobile', CURRENT_DATE + 12, 'Work on mobile PvP features', 'Great game mechanics', 'open'),
('CyberVania', 'Product Manager', '130k-150k', 'https://cybervania.com/jobs/pm', CURRENT_DATE + 13, 'Manage roadmap', 'Met PM at meetup', 'active'),
('Respawn Digital', 'SRE', '125k-145k', 'https://respawn.digital/jobs/sre', CURRENT_DATE + 14, 'Uptime focus', 'Interviewed last week', 'active'),
('MotherboardX', 'Security Engineer', '120k-140k', 'https://mbx.com/jobs/seceng', CURRENT_DATE + 15, 'Pen testing', 'Final round soon', 'active'),
('YoshiStack', 'Fullstack Dev', '110k-130k', 'https://yoshistack.io/jobs/fullstack', CURRENT_DATE + 16, 'End-to-end features', 'Tech test done', 'active'),
('DriftCircuit', 'Game Server Engineer', '115k-135k', 'https://driftcircuit.com/jobs/server', CURRENT_DATE + 17, 'Multiplayer infra', 'Awaiting next round', 'active'),
('RedRing Labs', 'QA Analyst', '80k-100k', 'https://redringlabs.com/jobs/qa', CURRENT_DATE + 18, 'Test regression cases', 'Rejected with feedback', 'rejected'),
('ManaCore', 'DevRel', '95k-110k', 'https://manacore.gg/jobs/devrel', CURRENT_DATE + 19, 'Engage community devs', 'Ghosted after recruiter chat', 'ghosted'),
('DarkZone AI', 'ML Ops', '120k-145k', 'https://darkzone.ai/jobs/mlops', CURRENT_DATE + 20, 'Maintain models in prod', 'No updates post final round', 'ghosted'),
('PixelForge', 'Art Pipeline Engineer', '100k-120k', 'https://pixelforge.gg/jobs/art-pipeline', CURRENT_DATE + 21, 'Automate asset pipelines', 'Ghosted after take-home', 'ghosted'),
('SynthCity', 'Infrastructure Engineer', '130k-150k', 'https://synthcity.tech/jobs/infra', CURRENT_DATE + 22, 'Modernize infra', 'Rejection after panel', 'rejected'),
('ChocoboCloud', 'Support Engineer', '80k-95k', 'https://chocobo.cloud/jobs/support', CURRENT_DATE + 23, 'Tech support workflows', 'Quick rejection', 'rejected'),
('StarByte Studios', 'Gameplay Engineer', '110k-130k', 'https://starbyte.dev/jobs/gameplay', CURRENT_DATE + 24, 'Gameplay mechanics', 'No reply after HR call', 'ghosted'),
('ShadowCode', 'Tooling Engineer', '105k-125k', 'https://shadowcode.gg/jobs/tooling', CURRENT_DATE + 25, 'Dev tools for creators', 'Ghosted after tech screen', 'ghosted');

-- Sample interviews data (linked to job applications)
INSERT INTO interview (
    job_application_id,
    interview_date,
    interviewer,
    description,
    status
)
VALUES
(1, CURRENT_DATE + 5, 'Zelda N.', 'Gameplay logic deep-dive', 'open'),
(2, CURRENT_DATE + 6, 'Cat Y.', 'ML architecture discussion', 'open'),
(3, CURRENT_DATE + 7, 'Bowser K.', 'Cloud deployment design', 'open'),
(4, CURRENT_DATE + 8, 'GLaDOS B.', 'Automated testing walk-through', 'open'),
(5, CURRENT_DATE + 9, 'Barret W.', 'CI/CD system review', 'open'),
(6, CURRENT_DATE + 10, 'Tidus A.', 'Level design critique', 'open'),
(7, CURRENT_DATE + 11, 'Echo V.', 'UX case study', 'open'),
(8, CURRENT_DATE + 12, 'Andrew R.', 'Networking concepts', 'open'),
(13, CURRENT_DATE + 13, 'Jill V.', 'Product case presentation', 'open'),
(14, CURRENT_DATE + 14, 'Octane S.', 'On-call rotation Q&A', 'open'),
(15, CURRENT_DATE + 15, 'Luigi M.', 'Security policy overview', 'open'),
(16, CURRENT_DATE + 16, 'Toad C.', 'Fullstack architecture deep dive', 'open');


-- Sample offers data (linked to job applications)
INSERT INTO offer (
    job_application_id,
    offer_date,
    salary_offered,
    description
)
VALUES
(1, CURRENT_DATE + 20, '120k', 'Offer from Hyrule Systems for Software Engineer'),
(2, CURRENT_DATE + 21, '135k', 'Offer from NekoTech for AI Engineer'),
(6, CURRENT_DATE + 22, '105k', 'Offer from Zanarkand Dynamics for Game Designer'),
(14, CURRENT_DATE + 23, '140k', 'Offer from Respawn Digital for SRE'),
(15, CURRENT_DATE + 24, '138k', 'Offer from MotherboardX for Security Engineer');

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

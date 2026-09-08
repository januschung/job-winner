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
('Anthropic', 'Software Engineer', '200k-280k', 'https://www.anthropic.com/careers', CURRENT_DATE - 28, 'Build reliable AI product surfaces', 'Recruiter screen booked', 'open'),
('OpenAI', 'Full Stack Engineer', '210k-290k', 'https://openai.com/careers', CURRENT_DATE - 26, 'Ship features across web and APIs', 'Waiting on take-home', 'open'),
('Cursor', 'Backend Engineer', '180k-250k', 'https://cursor.com/careers', CURRENT_DATE - 24, 'Scale collaborative editing services', 'Strong culture fit so far', 'open'),
('Vercel', 'Platform Engineer', '170k-230k', 'https://vercel.com/careers', CURRENT_DATE - 22, 'Improve deploy and edge tooling', 'Shared portfolio projects', 'open'),
('Cloudflare', 'Systems Engineer', '175k-240k', 'https://www.cloudflare.com/careers', CURRENT_DATE - 20, 'Work on global edge networking', 'Phone screen next week', 'open'),
('Datadog', 'Software Engineer', '165k-220k', 'https://careers.datadoghq.com', CURRENT_DATE - 18, 'Observability product development', 'Liked their on-call model', 'open'),
('Notion', 'Fullstack Engineer', '160k-220k', 'https://www.notion.so/careers', CURRENT_DATE - 16, 'Editor performance and sync', 'Applied via referral', 'open'),
('Figma', 'Infrastructure Eng', '175k-235k', 'https://www.figma.com/careers', CURRENT_DATE - 14, 'Realtime collab infrastructure', 'Waiting on recruiter', 'open'),
('Linear', 'Product Engineer', '155k-210k', 'https://linear.app/careers', CURRENT_DATE - 12, 'Craft fast issue-tracking UX', 'Small team, high ownership', 'open'),
('Stripe', 'API Engineer', '185k-250k', 'https://stripe.com/jobs', CURRENT_DATE - 10, 'Payments APIs and reliability', 'Referral from ex-coworker', 'active'),
('Shopify', 'Backend Engineer', '160k-210k', 'https://www.shopify.com/careers', CURRENT_DATE - 9, 'Commerce platform services', 'Onsite loop scheduled', 'active'),
('Airbnb', 'SRE', '180k-240k', 'https://careers.airbnb.com', CURRENT_DATE - 8, 'Reliability for booking systems', 'System design went well', 'active'),
('Discord', 'Backend Engineer', '170k-225k', 'https://discord.com/careers', CURRENT_DATE - 7, 'Realtime messaging scale', 'Pairing interview done', 'active'),
('Rippling', 'Software Engineer', '165k-220k', 'https://www.rippling.com/careers', CURRENT_DATE - 6, 'HR/IT platform services', 'Final round this week', 'active'),
('Coinbase', 'Security Engineer', '180k-250k', 'https://www.coinbase.com/careers', CURRENT_DATE - 5, 'Application security reviews', 'Security challenge submitted', 'active'),
('Netflix', 'Cloud Engineer', '190k-260k', 'https://jobs.netflix.com', CURRENT_DATE - 21, 'Streaming cloud infrastructure', 'Rejected after panel', 'rejected'),
('Uber', 'Mobile Engineer', '160k-210k', 'https://www.uber.com/careers', CURRENT_DATE - 19, 'Rider app feature work', 'Quick rejection email', 'rejected'),
('Lyft', 'Data Engineer', '155k-205k', 'https://www.lyft.com/careers', CURRENT_DATE - 17, 'Pipeline work for marketplace', 'No update after screen', 'ghosted'),
('DoorDash', 'Backend Engineer', '160k-215k', 'https://careers.doordash.com', CURRENT_DATE - 15, 'Logistics matching services', 'Ghosted after take-home', 'ghosted'),
('Snap', 'iOS Engineer', '170k-230k', 'https://careers.snap.com', CURRENT_DATE - 13, 'Camera and messaging features', 'Silence after recruiter call', 'ghosted');

-- Sample interviews data (linked to job applications)
INSERT INTO interview (
    job_application_id,
    interview_date,
    interviewer,
    description,
    status
)
VALUES
(1, CURRENT_DATE - 21, 'Priya Nair', 'Intro call and role overview', 'expired'),
(1, CURRENT_DATE - 3, 'Alex Morgan', 'System design: rate limiter', 'open'),
(2, CURRENT_DATE - 2, 'Sam Patel', 'Take-home review discussion', 'open'),
(3, CURRENT_DATE + 2, 'Jordan Lee', 'Backend deep dive on APIs', 'open'),
(4, CURRENT_DATE + 3, 'Casey Brooks', 'Platform tooling walkthrough', 'open'),
(10, CURRENT_DATE - 4, 'Riley Chen', 'API design and idempotency', 'open'),
(10, CURRENT_DATE + 1, 'Morgan Diaz', 'Behavioral + ownership stories', 'open'),
(11, CURRENT_DATE - 1, 'Taylor Kim', 'Live coding: cart checkout', 'open'),
(12, CURRENT_DATE + 4, 'Chris Alvarez', 'SRE incident response case', 'open'),
(13, CURRENT_DATE + 5, 'Jamie Ortiz', 'Realtime fanout architecture', 'open'),
(14, CURRENT_DATE + 2, 'Avery Quinn', 'Hiring manager conversation', 'open'),
(15, CURRENT_DATE - 2, 'Nina Shah', 'Threat modeling exercise', 'open');

-- Sample offers data (linked to job applications)
INSERT INTO offer (
    job_application_id,
    offer_date,
    salary_offered,
    description
)
VALUES
(10, CURRENT_DATE - 1, '215k + equity', 'Stripe verbal offer pending written packet'),
(12, CURRENT_DATE, '205k + RSU', 'Airbnb SRE offer, 2 weeks to decide'),
(14, CURRENT_DATE + 1, '195k + bonus', 'Rippling offer after final loop');

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
('Walk me through a system you designed end to end.',
 'I designed a job-tracking API with WebFlux, R2DBC, and Postgres. I split reads/writes cleanly, used Flyway for schema changes, and added GraphQL for the UI so clients fetch only needed fields.'),
('How do you approach debugging a production incident?',
 'I start with blast radius and recent deploys, check dashboards and logs, then form a hypothesis. I mitigate first (rollback or feature flag), then write a short postmortem with action items.'),
('Tell me about a time you disagreed with a teammate.',
 'On API versioning I preferred explicit v2 endpoints over silent breaking changes. I proposed a short RFC, we aligned on deprecation windows, and shipped without customer downtime.'),
('How do you ensure code quality without slowing delivery?',
 'I keep PRs small, require tests for critical paths, and automate lint/format in CI. For risky areas I add contract tests so reviews focus on design, not style nits.'),
('Why are you interested in this role?',
 'I want to work on high-leverage product infrastructure where reliability and developer experience matter. This team’s stack and ownership model match how I like to build.'),
('Explain eventual consistency to a non-expert.',
 'It means different parts of the system may briefly disagree after a write, then catch up. Like group chat: your message shows instantly for you, then appears for others a moment later.'),
('What would you improve in our interview process?',
 'I would ask for a realistic take-home scoped to 2 hours max, then discuss tradeoffs live. That shows how candidates think without consuming a full weekend.');

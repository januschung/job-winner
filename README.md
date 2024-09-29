# Job Winner


## Background

I lost my job right before the holiday season in 2022. I need to look for a new job and apply for as many as I can while all the big techs are laying off talents.

To keep track of job applications:

1. Use Google Spreadsheet (I am using it before this project). It is ugly, hard to maintain and easy to mess up the layout.
1. Use online tool like huntr.co. But it can only track 40 jobs and I don't want to spend a dime for the premium service.
1. Use pen and paper. It works but I can't copy and paste information such as the job url link and utilize it later.

None of the about is fun or totally fit my use case.

That's the reason for me to build something for myself, and potentially you, to get the job done (and to get a new job successfully!)

## Benefit of Job Winner

With Job Winner, you can keep track of how many job applications you want, for free, without paying for a fee and have your personal data being sold.

Another feature of Job Winner is the `Profile Page`. With that one can store useful information a job application usually asks for (eg. LinkedIn url) in a single section. Besides that, the `Profile Page` provides a handy feature to automatically copy the field that you click on to your clipboard.

## Features

Current function:
1. An Index page to list all the job applications
![index](readme-img/index.png)
1. Create new job application
![new](readme-img/add.png)
1. Delete a job application
1. Edit an existing job application
![edit](readme-img/edit.png)
1. A profile page to store personal information
![profile](readme-img/profile.png)

Each job application has the following fields:
1. Company Name
1. Job Title
1. Salary Range
1. Applied Date
1. Description
1. Job Link
1. Status

The personal page has the following fields:
1. First Name
1. Last Name
1. Address Street 1
1. Address Street 2
1. Address City
1. Address State
1. Address Zip
1. Linkedin url
1. Github url
1. Personal Website url

## Components

Job Winner has two components
1. Backend (this repo)
1. [UI](https://github.com/januschung/job-winner-ui)

## About this repo

This is the backend for Job Winner and here is the stack:

1. Java 17
1. Spring Boot 3.0.1
1. Spring Reactive
1. GraphQL
1. Postgres DB

## To build 

Prereq: Java17

1. Bring up the Postgres DB with docker-compose
```console
docker-compose up -d
```
1. Create the db schema with `src/main/resources/schema.sql`
    1. Visit http://localhost:8081/. Information can be found or modified [here](./src/main/resources/application.properties).
        1. User: `postgres`.
        1. Password: `example`.
        1. Click on `Import` and follow instructions to import file.
1. Build the java app with Maven
```console
mvn clean install
java -jar target/job-winner-0.0.1-SNAPSHOT.jar
```

Please follow the build instruction from the [UI repo](https://github.com/januschung/job-winner-ui) to bring up the UI.

## Contributing 

I would like your help and input.

I appreciate all suggestions or PRs which will help this project better. Feel free to fork the project and create a pull request with your idea.

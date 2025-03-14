# Job Winner

## Live Demo

🚀 Check out the live demo! 👉 https://jobwinner.duckdns.org

✨ The data refreshes automatically every hour, so feel free to explore and test out any feature!

Let me know if you'd like any further tweaks by submitting an issue!

## Background

I lost my job right before the holiday season in 2022. I need to look for a new job and apply for as many as I can while all the big techs are laying off talents.

To keep track of job applications:

1. Use Google Spreadsheet (I am using it before this project). It is ugly, hard to maintain and easy to mess up the layout.
1. Use online tool like huntr.co. But it can only track 40 jobs and I don't want to spend a dime for the premium service.
1. Use pen and paper. It works but I can't copy and paste information such as the job url link and utilize it later.

None of the about is fun or totally fit my use case.

That's the reason for me to build something for myself, and potentially you, to get the job done (and to get a new job successfully!)

## Benefit of Job Winner

Job Winner helps you keep track of all your job applications in one place—totally free! No hidden fees, and you won't have to worry about your personal data being sold.

One of the handy features is the Profile Page. This is where you can store all the personal information that job applications often ask for (like your LinkedIn URL). Plus, it has a super handy feature that lets you copy any field to your clipboard with just a click.

## Features

Key Functions:
1. __Index Page:__

    View a list of all your job applications in one place, with easy access to each application’s details and status.
    ![index](readme-img/index.png)
1. __Create New Job Application:__

    Easily add new job applications to your list with a user-friendly form.
    ![new](readme-img/add.png)
1. __Delete Job Application:__

    Remove any applications you no longer need with a simple delete option.
1. __Edit Job Application:__

    Make updates to your existing job applications as the status of your applications change or new information becomes available.
    ![edit](readme-img/edit.png)
    - __Manage Interview:__

      Keep track of your interviews with the ability to add and manage interview details.
      ![edit](readme-img/interview.png)
    - __Manage Offer:__

      Track any job offers you’ve received, including details about the salary and offer date.
      ![edit](readme-img/offer.png)
1. __Profile Page:__
    
    Store and manage all your personal information in one spot
    ![profile](readme-img/profile.png)
1. __Interview List:__
  
    Keep track of all your interviews in one place, with easy-to-view details and statuses plus sortable headers.
    ![profile](readme-img/interview-list.png)
1. __Offer List:__
    
    Track all job offers you’ve received with sortable headers.
    ![profile](readme-img/offer-list.png)

1. __Search and Filter:__
    
    Quickly search for specific job applications based on keywords to help you stay organized. Whether it's the company name, job title, or description, finding the right job application is easy.
1. __Interview and Offer Count:__
    
    Stay on top of your job search with an overview of how many interviews and offers you currently have, making it easier to manage multiple opportunities.

## Components

Job Winner has two components
1. Backend (this repo)
1. [UI](https://github.com/januschung/job-winner-ui)

## About this repo

This is the backend for Job Winner and here is the stack:

1. Java 17
1. Spring Boot 3.4.0
1. Spring Reactive
1. GraphQL
1. Postgres DB

## Build and Run the App with Docker Compose

You have two options for data persistence:
* **PostgreSQL**, a SQL database instance suitable for production releases
* **H2**, a much simpler in-memory store (persisted in a file) that is useful for local testing

### PostgreSQL

1. Build the App with docker compose
```console
docker compose up builder
```
2. Run the App with docker compose. This will initialize the database automatically.
```console
docker compose up -d
```

3. Administer the database:
    * Visit http://localhost:8081/. Information can be found or modified [here](./src/main/resources/application.properties).
        * System: `PostgreSQL`
        * Server: `db`
        * User: `postgres`
        * Password: `example`
        * Database: `postgres`

Please follow the build instruction from the [UI repo](https://github.com/januschung/job-winner-ui) to bring up the UI.

### H2

1. Build the App with docker compose
```console
docker compose up builder
```

2. Initialize the H2 database and run the app (**Do this the first time you run it**)
```console
SPRING_SQL_INIT_MODE=always docker compose up app-h2
```

3. Subsequent runs can skip the initialization:
``` console
docker compose up app-h2
```

Please follow the build instruction from the [UI repo](https://github.com/januschung/job-winner-ui) to bring up the UI.

## Contributing

We welcome contributions! If you'd like to help improve Job Winner, here's how you can contribute:

1. Fork the repository.
1. Create a new branch for your feature or fix.
1. Make your changes and ensure the tests are passing.
1. Submit a pull request with a description of the changes you've made.

Feel free to open an issue if you have any questions or suggestions!

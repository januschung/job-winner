package com.tnite.jobwinner.controller;


import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.repo.JobApplicationRepository;


@SpringBootTest
@AutoConfigureGraphQlTester
public class JobApplicationGraphQlTest {

    @Autowired
    private GraphQlTester graphQlTester;
    
    @Autowired
    JobApplicationRepository jobApplicationRepository;
    
    @BeforeEach
    void setUp() {
      List<JobApplication> jobApplications = List.of(new JobApplication(null, "A", "SWE", "100-200", "http://a.com", LocalDate.now(), "whatever1", "whatever1"),
      new JobApplication(null, "B", "DE", "110-210", "http://b.com", LocalDate.now(), "whatever2", "whatever2"),
      new JobApplication(null, "C", "QA", "120-220", "http://c.com", LocalDate.now(), "whatever3", "whatever3"));
            jobApplicationRepository.deleteAll()
              .thenMany(jobApplicationRepository.saveAll(jobApplications)).log().blockLast();
    }
        
    
        
    @Test
    void allJobApplicationTest() {
        
        String document = """
        query {
              allJobApplication {
                    id, description, jobTitle, jobUrl, status, appliedDate
              }
            }
        """;
        
        graphQlTester.document(document)
            .execute()
            .path("allJobApplication")
            .entityList(JobApplication.class)
            .hasSize(3);
        
    }
    
    @Test
    void addJobApplicationTest() {
        
        String document = """
        mutation{
          addJobApplication(addJobApplicationInput: {
            companyName:"some copmany",
            jobTitle:"some title"
            salaryRange:"100-200",
            appliedDate:"2023-01-01",
            status:"whatever",
            jobUrl: "some url",
            description: "whatever"
          }) {
            id, jobTitle, description
          }
        }
        """;
        
        graphQlTester.document(document)
            .execute()
            .path("addJobApplication.description")
            .entity(String.class)
            .isEqualTo("whatever");
        
    }
        
    @Test
    void deleteApplicationTest() {
        
        String document = """
        mutation{
          deleteJobApplication(id:1) {
            id, description, status, jobTitle
          }
        }
        """;
        
        graphQlTester.document(document)
            .execute()
            .path("deleteJobApplication.jobTitle")
            .entity(String.class)
            .isEqualTo("SWE");
        
    }



}

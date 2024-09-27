package com.tnite.jobwinner.controller;


import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.repo.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureGraphQlTester
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
    "spring.r2dbc.url=r2dbc:h2:mem:///testdb-${random.uuid}",
    "spring.r2dbc.generate-unique-name=true",
})
@ExtendWith(MockitoExtension.class)
public class JobApplicationGraphQlTest {

    @Autowired
    private GraphQlTester graphQlTester;
    
    @MockBean
    JobApplicationRepository jobApplicationRepository;

    private JobApplication jobApplication1;
    private JobApplication jobApplication2;
    private JobApplication jobApplication3;
    private JobApplication jobApplication4;
    private JobApplication jobApplication1_updated;
    private List<JobApplication> jobApplicationList;

    @BeforeEach
    void setUp() {
        jobApplication1 = new JobApplication();
        jobApplication1.setId(1);
        jobApplication1.setCompanyName("Company A");
        jobApplication1.setJobTitle("Developer");
        jobApplication1.setAppliedDate(LocalDate.of(2023, 9, 10));
        jobApplication1.setDescription("Description A");
        jobApplication1.setSalaryRange("1000-2000");
        jobApplication1.setJobUrl("http://companyA.com");
        jobApplication1.setStatus("Applied");

        jobApplication2 = new JobApplication();
        jobApplication2.setId(2);
        jobApplication2.setCompanyName("Company B");
        jobApplication2.setJobTitle("Manager");
        jobApplication2.setAppliedDate(LocalDate.of(2023, 9, 11));
        jobApplication2.setDescription("Description B");
        jobApplication2.setSalaryRange("2000-3000");
        jobApplication2.setJobUrl("http://companyB.com");
        jobApplication2.setStatus("Interview");

        jobApplication3 = new JobApplication();
        jobApplication3.setId(3);
        jobApplication3.setCompanyName("Company C");
        jobApplication3.setJobTitle("Tester");
        jobApplication3.setAppliedDate(LocalDate.of(2023, 9, 12));
        jobApplication3.setDescription("Description C");
        jobApplication3.setSalaryRange("1500-2500");
        jobApplication3.setJobUrl("http://companyC.com");
        jobApplication3.setStatus("Offer");

        jobApplicationList = Arrays.asList(jobApplication1, jobApplication2, jobApplication3);

        jobApplication4 = new JobApplication();
        jobApplication4.setId(4);
        jobApplication4.setCompanyName("some copmany");
        jobApplication4.setJobTitle("some title");
        jobApplication4.setAppliedDate(LocalDate.of(2023, 1, 1));
        jobApplication4.setDescription("whatever");
        jobApplication4.setSalaryRange("100-200");
        jobApplication4.setJobUrl("some url");
        jobApplication4.setStatus("whatever");

        jobApplication1_updated = new JobApplication();
        jobApplication1_updated.setId(1);
        jobApplication1_updated.setCompanyName("some copmany");
        jobApplication1_updated.setJobTitle("some title");
        jobApplication1_updated.setAppliedDate(LocalDate.of(2023, 1, 1));
        jobApplication1_updated.setDescription("whatever");
        jobApplication1_updated.setSalaryRange("100-200");
        jobApplication1_updated.setJobUrl("some url");
        jobApplication1_updated.setStatus("whatever");
    }

    @Test
    void testAllJobApplication() {

        when(jobApplicationRepository.findAll()).thenReturn(Flux.fromIterable(jobApplicationList));

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
    void testAddJobApplication() {

        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(Mono.just(jobApplication4));

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
    void testUpdateJobApplication() {

        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(Mono.just(jobApplication1_updated));

        String document = """
        mutation UpdateJobApplication {
            updateJobApplication(jobApplication: {
                id: 1
                companyName:"some copmany",
                jobTitle:"some title"
                salaryRange:"100-200",
                appliedDate:"2023-01-01",
                status:"whatever",
                jobUrl: "some url",
                description: "whatever"
            }) {
                id
                companyName
                jobTitle
                salaryRange
                appliedDate
                status
                jobUrl
                description
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("updateJobApplication.status")
            .entity(String.class)
            .isEqualTo("whatever");

        verify(jobApplicationRepository, times(1)).findById(1);
        verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
    }

    @Test
    void testDeleteApplication() {
        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));
        when(jobApplicationRepository.delete(jobApplication1)).thenReturn(Mono.empty());

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
            .entity(String.class) // This assertion is to check that the mutation is executed
            .isEqualTo("Developer");

    }

    @Test
    void testSearchJobApplications() {
        when(jobApplicationRepository.searchJobApplications(anyString())).thenReturn(Flux.fromIterable(jobApplicationList));
        graphQlTester.document(getSearchQueryFromSearchTerm("whatever"))
            .execute()
            .path("searchJobApplications")
            .entityList(JobApplication.class)
            .hasSize(3);
    }

    @Test
    void testSearchJobApplicationsWhenOnlyOneMatches() {
        when(jobApplicationRepository.searchJobApplications(anyString())).thenReturn(Flux.just(jobApplication1));
        graphQlTester.document(getSearchQueryFromSearchTerm("one"))
            .execute()
            .path("searchJobApplications")
            .entityList(JobApplication.class)
            .hasSize(1);
    }

    @Test
    void testSearchJobApplicationsWhenNoMatch() {
        when(jobApplicationRepository.searchJobApplications(anyString())).thenReturn(Flux.empty());
        graphQlTester.document(getSearchQueryFromSearchTerm("bogus"))
            .execute()
            .path("searchJobApplications")
            .entityList(JobApplication.class)
            .hasSize(0);
    }

    private String getSearchQueryFromSearchTerm(String searchTerm) {
        return String.format("""
        query {
            searchJobApplications(searchTerm: "%s") {
                id
                companyName
                jobTitle
            }
        }
        """, searchTerm);
    }

}

package com.tnite.jobwinner.controller;


import com.tnite.jobwinner.model.Interview;
import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.repo.InterviewRepository;
import com.tnite.jobwinner.repo.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureGraphQlTester
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
	"spring.r2dbc.url=r2dbc:h2:mem:///testdb-${random.uuid}",
	"spring.r2dbc.generate-unique-name=true",
})
public class InterviewGraphQlTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    InterviewRepository interviewRepository;

    @MockBean
    JobApplicationRepository jobApplicationRepository;

    private Interview interview1;
    private Interview interview2;
    private Interview interview3;
    private Interview interview1_updated;
    private JobApplication jobApplication1;
    private JobApplication jobApplication2;

    @BeforeEach
    void setUp() {
        jobApplication1 = new JobApplication(1, "Company A", "QA", "", "", LocalDate.now(), "", "", "abc");
        jobApplication2 = new JobApplication(2, "Company B", "QA", "", "", LocalDate.now(), "", "", "open");
        interview1 = new Interview(1, 1, LocalDate.of(2024, 9, 2), "John Doe", "Technical interview", "scheduled", jobApplication1);
        interview2 = new Interview(2, 1, LocalDate.now(), "Jane Doe", "HR interview", "closed", jobApplication1);
        interview3 = new Interview(3, 2, LocalDate.now(), "Jane Doe", "HR interview", "scheduled", jobApplication2);
        interview1_updated = new Interview(1, 1, LocalDate.of(2024, 9, 2), "John Doe", "Technical interview", "closed", jobApplication1);
    }

    @Test
    void testAllInterview() {
        when(interviewRepository.findAll()).thenReturn(Flux.fromIterable(List.of(interview1, interview2, interview3)));
        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));
        when(jobApplicationRepository.findById(2)).thenReturn(Mono.just(jobApplication2));

        String document = """
        query {
            allInterview {
                id
                jobApplicationId
                interviewDate
                interviewer
                description
                status
                jobApplication {
                    companyName
                }
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("allInterview")
            .entityList(Interview.class)
            .hasSize(3)
            .satisfies(offers -> {
                assertEquals(offers.get(0).getJobApplication().getCompanyName(), "Company A");
                assertEquals(offers.get(1).getJobApplication().getCompanyName(), "Company A");
                assertEquals(offers.get(2).getJobApplication().getCompanyName(), "Company B");
            });

        verify(interviewRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(interviewRepository.findById(1)).thenReturn(Mono.just(interview1));
        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));

        String document = """
        query {
            interviewById(id: 1) {
                id
                jobApplicationId
                interviewDate
                interviewer
                description
                status
                jobApplication {
                    id
                    companyName
                    jobTitle
                    salaryRange
                    jobUrl
                    appliedDate
                    description
                    note
                    status
                }
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("interviewById")
            .entity(Interview.class)
            .isEqualTo(interview1);

        verify(interviewRepository, times(1)).findById(1);
    }

	@Test
	void testAllInterviewByJobApplicationId() {
		when(interviewRepository.findAllByJobApplicationId(1)).thenReturn(Flux.fromIterable(List.of(interview1, interview2)));
        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));
        when(jobApplicationRepository.findById(2)).thenReturn(Mono.just(jobApplication2));

        String document = """
        query {
            allInterviewByJobApplicationId(jobApplicationId: 1) {
                id
                jobApplicationId
                interviewDate
                interviewer
                description
                status
                jobApplication {
                    id
                    companyName
                    jobTitle
                    salaryRange
                    jobUrl
                    appliedDate
                    description
                    note
                    status
                }
            }
        }
        """;

		graphQlTester.document(document)
			.execute()
			.path("allInterviewByJobApplicationId")
			.entityList(Interview.class)
			.hasSize(2)
			.contains(interview1, interview2);

		verify(interviewRepository, times(1)).findAllByJobApplicationId(1);
	}

    @Test
    void testAllInterviewByJobApplicationIdWhenOnlyOneMatches() {
        when(interviewRepository.findAllByJobApplicationId(2)).thenReturn(Flux.just(interview3));
        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication2));

        String document = """
        query {
            allInterviewByJobApplicationId(jobApplicationId: 2) {
                id
                jobApplicationId
                interviewDate
                interviewer
                description
                status
                jobApplication {
                    id
                    companyName
                    jobTitle
                    salaryRange
                    jobUrl
                    appliedDate
                    description
                    note
                    status
                }
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("allInterviewByJobApplicationId")
            .entityList(Interview.class)
            .hasSize(1)
            .contains(interview3);

        verify(interviewRepository, times(1)).findAllByJobApplicationId(2);
    }

    @Test
    void testAllInterviewByJobApplicationIdWhenNoMatch() {
        when(interviewRepository.findAllByJobApplicationId(999)).thenReturn(Flux.empty());

        String document = """
        query {
            allInterviewByJobApplicationId(jobApplicationId: 999) {
                id
                jobApplicationId
                interviewDate
                interviewer
                description
                status
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("allInterviewByJobApplicationId")
            .entityList(Interview.class)
            .hasSize(0);

        verify(interviewRepository, times(1)).findAllByJobApplicationId(999);
    }

	@Test
	void testAddInterview() {
		when(interviewRepository.save(any(Interview.class))).thenReturn(Mono.just(interview1));
        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));

        String document = """
        mutation AddInterview {
            addInterview(interviewInput: {
                jobApplicationId: 1,
                interviewDate: "2024-09-02",
                interviewer: "John Doe",
                description: "Technical interview",
                status: "scheduled"
            }) {
                id
                jobApplicationId
                interviewDate
                interviewer
                description
                status
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("addInterview.description")
			.entity(String.class)
			.isEqualTo("Technical interview");

		verify(interviewRepository, times(1)).save(any(Interview.class));
	}

	@Test
	void testUpdateInterview() {
		when(interviewRepository.findById(1)).thenReturn(Mono.just(interview1));
        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));
        when(interviewRepository.save(any(Interview.class))).thenReturn(Mono.just(interview1_updated));

        String document = """
        mutation UpdateInterview {
            updateInterview(id: 1, interview: {
                id: 1,
                jobApplicationId: 1,
                interviewDate: "2024-09-02",
                interviewer: "John Doe",
                description: "Technical interview",
                status: "closed"
            }) {
                id
                jobApplicationId
                interviewDate
                interviewer
                description
                status
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("updateInterview.status")
            .entity(String.class)
            .isEqualTo("closed");

		verify(interviewRepository, times(1)).findById(1);
		verify(interviewRepository, times(1)).save(any(Interview.class));
	}

    @Test
    void testDeleteOffer() {
        when(interviewRepository.findById(1)).thenReturn(Mono.just(interview1));
//        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));
        when(interviewRepository.delete(interview1)).thenReturn(Mono.empty());

        String document = """
        mutation {
            deleteInterview(id:1) {
                id
                jobApplicationId
                interviewDate
                interviewer
                description
                status
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("deleteInterview.description")
            .entity(String.class)
            .isEqualTo("Technical interview");

        verify(interviewRepository, times(1)).findById(1);
        verify(interviewRepository, times(1)).delete(interview1);
    }
}

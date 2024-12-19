package com.tnite.jobwinner.controller;


import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.model.Offer;
import com.tnite.jobwinner.repo.JobApplicationRepository;
import com.tnite.jobwinner.repo.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
public class OfferGraphQlTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    OfferRepository offerRepository;

    @MockBean
    JobApplicationRepository jobApplicationRepository;

    private Offer offer1;
    private Offer offer2;
    private Offer offer3;
    private Offer offer1_updated;
    private JobApplication jobApplication1;
    private JobApplication jobApplication2;
    private JobApplication jobApplication3;

    @BeforeEach
    void setUp() {
        jobApplication1 = new JobApplication(1, "Company A", "QA", "", "", LocalDate.now(), "", "abc");
        jobApplication2 = new JobApplication(2, "Company B", "QA", "", "", LocalDate.now(), "", "open");
        jobApplication3 = new JobApplication(3, "Company C", "QA", "", "", LocalDate.now(), "", "open");

        offer1 = new Offer(1, 1, LocalDate.of(2024, 9, 2), "1", "whatever1", jobApplication1);
        offer2 = new Offer(2, 2, LocalDate.of(2024, 9, 2), "2", "whatever2", jobApplication2);
        offer3 = new Offer(3, 3, LocalDate.of(2024, 9, 2), "3", "whatever3", jobApplication3);
        offer1_updated = new Offer(1, 1, LocalDate.of(2024, 9, 2), "10", "whatsoever1", jobApplication1);
    }

    @Test
    void testAllOffer() {
        when(offerRepository.findAll()).thenReturn(Flux.fromIterable(List.of(offer1, offer2, offer3)));
        when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));
        when(jobApplicationRepository.findById(2)).thenReturn(Mono.just(jobApplication2));
        when(jobApplicationRepository.findById(3)).thenReturn(Mono.just(jobApplication3));

        String document = """
        query {
            allOffer {
                id
                jobApplicationId
                offerDate
                salaryOffered
                description
                jobApplication {
                    companyName
                }
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("allOffer")
            .entityList(Offer.class)
            .hasSize(3)
            .satisfies(offers -> {
                assertEquals(offers.get(0).getJobApplication().getCompanyName(), "Company A");
                assertEquals(offers.get(1).getJobApplication().getCompanyName(), "Company B");
                assertEquals(offers.get(2).getJobApplication().getCompanyName(), "Company C");
            });

        verify(offerRepository, times(1)).findAll();
        verify(jobApplicationRepository, times(3)).findById(anyInt());
    }

    @Test
    void testOfferByJobApplicationId() {
        when(offerRepository.findByJobApplicationId(1)).thenReturn(Mono.just(offer1));

        String document = """
        query {
            offerByJobApplicationId(jobApplicationId: 1) {
                id
                jobApplicationId
                offerDate
                salaryOffered
                description
                jobApplication {
                    id
                    companyName
                    jobTitle
                    salaryRange
                    jobUrl
                    appliedDate
                    description
                    status
                }
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("offerByJobApplicationId")
            .entity(Offer.class)
            .isEqualTo(offer1);

        verify(offerRepository, times(1)).findByJobApplicationId(1);
    }

	@Test
	void testAddOffer() {
		when(offerRepository.save(any(Offer.class))).thenReturn(Mono.just(offer1));

        String document = """
        mutation AddOffer {
            addOffer(offerInput: {
                jobApplicationId: 1,
                offerDate: "2024-09-02",
                salaryOffered: "1",
                description: "whatever1",
            }) {
                id
                jobApplicationId
                offerDate
                salaryOffered
                description
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("addOffer.description")
			.entity(String.class)
			.isEqualTo("whatever1");

		verify(offerRepository, times(1)).save(any(Offer.class));
	}

	@Test
	void testUpdateOffer() {
		when(offerRepository.findById(1)).thenReturn(Mono.just(offer1));
        when(offerRepository.save(any(Offer.class))).thenReturn(Mono.just(offer1_updated));

        String document = """
        mutation UpdateOffer {
            updateOffer(offer: {
                id: 1,
                jobApplicationId: 1,
                offerDate: "2024-09-02",
                salaryOffered: "10",
                description: "whatsoever1",
            }) {
                id
                jobApplicationId
                offerDate
                salaryOffered
                description
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("updateOffer.description")
            .entity(String.class)
            .isEqualTo("whatsoever1");

		verify(offerRepository, times(1)).findById(1);
		verify(offerRepository, times(1)).save(any(Offer.class));
	}

    @Test
    void testDeleteOffer() {
        when(offerRepository.findById(1)).thenReturn(Mono.just(offer1));
        when(offerRepository.delete(offer1)).thenReturn(Mono.empty());

        String document = """
        mutation {
            deleteOffer(id:1) {
                id
                jobApplicationId
                offerDate
                salaryOffered
                description
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("deleteOffer.description")
            .entity(String.class)
            .isEqualTo("whatever1");

        verify(offerRepository, times(1)).findById(1);
        verify(offerRepository, times(1)).delete(offer1);
    }
}

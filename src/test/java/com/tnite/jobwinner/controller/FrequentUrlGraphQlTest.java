package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.FrequentUrl;
import com.tnite.jobwinner.repo.FrequentUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureGraphQlTester
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
    "spring.r2dbc.url=r2dbc:h2:mem:///testdb-${random.uuid}",
    "spring.r2dbc.generate-unique-name=true",
})
@ExtendWith(MockitoExtension.class)
public class FrequentUrlGraphQlTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    FrequentUrlRepository frequentUrlRepository;

    private FrequentUrl frequentUrl1;
    private FrequentUrl frequentUrl2;
    private FrequentUrl frequentUrl3;
    private FrequentUrl frequentUrl1_updated;
    private List<FrequentUrl> frequentUrlList;

    @BeforeEach
    void setUp() {
        frequentUrl1 = new FrequentUrl();
        frequentUrl1.setId(1);
        frequentUrl1.setTitle("Foo");
        frequentUrl1.setUrl("https://foo.com");

        frequentUrl2 = new FrequentUrl();
        frequentUrl2.setId(2);
        frequentUrl2.setTitle("Bar");
        frequentUrl2.setUrl("https://bar.com");

        frequentUrl3 = new FrequentUrl();
        frequentUrl3.setId(3);
        frequentUrl3.setTitle("Foo Bar");
        frequentUrl3.setUrl("https://foobar.com");

        frequentUrlList = Arrays.asList(frequentUrl1, frequentUrl2);

        frequentUrl1_updated = new FrequentUrl();
        frequentUrl1_updated.setId(1);
        frequentUrl1_updated.setTitle("Foo Edited");
        frequentUrl1_updated.setUrl("https://foo.com/edited");
    }

    @Test
    void testAllFrequentUrl() {

        when(frequentUrlRepository.findAll()).thenReturn(Flux.fromIterable(frequentUrlList));

        String document = """
        query {
            allFrequentUrl {
                id, title, url
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("allFrequentUrl")
            .entityList(FrequentUrl.class)
            .hasSize(2);
    }

    @Test
    void testAddFrequentUrl() {

        when(frequentUrlRepository.save(any(FrequentUrl.class))).thenReturn(Mono.just(frequentUrl3));

        String document = """
        mutation{
            addFrequentUrl(frequentUrlInput: {
                title:"Foo Bar"
                url: "https://foobar.com"
            }) {
                id, title, url
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("addFrequentUrl.title")
            .entity(String.class)
            .isEqualTo("Foo Bar");

    }

    @Test
    void testUpdateFrequentUrl() {

        when(frequentUrlRepository.findById(1)).thenReturn(Mono.just(frequentUrl1));
        when(frequentUrlRepository.save(any(FrequentUrl.class))).thenReturn(Mono.just(frequentUrl1_updated));

        String document = """
        mutation UpdateFrequentUrl {
            updateFrequentUrl(frequentUrl: {
                id: 1
                title:"Foo Edited"
                url: "https://foo.com/edited"
            }) {
                id
                title
                url
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("updateFrequentUrl.url")
            .entity(String.class)
            .isEqualTo("https://foo.com/edited");

        verify(frequentUrlRepository, times(1)).findById(1);
        verify(frequentUrlRepository, times(1)).save(any(FrequentUrl.class));
    }

    @Test
    void testDeleteFrequentUrl() {
        when(frequentUrlRepository.findById(1)).thenReturn(Mono.just(frequentUrl1));
        when(frequentUrlRepository.delete(frequentUrl1)).thenReturn(Mono.empty());

        String document = """
        mutation {
            deleteFrequentUrl(id:1) {
                id
                title
                url
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("deleteFrequentUrl.title")
            .entity(String.class)
            .isEqualTo("Foo");

        verify(frequentUrlRepository, times(1)).findById(1);
        verify(frequentUrlRepository, times(1)).delete(frequentUrl1);
    }

}

package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.Question;
import com.tnite.jobwinner.repo.QuestionRepository;
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
public class QuestionGraphQlTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    QuestionRepository questionRepository;

    private Question question1;
    private Question question2;
    private Question question3;
    private Question question1_updated;
    private List<Question> questionList;

    @BeforeEach
    void setUp() {
        question1 = new Question();
        question1.setId(1);
        question1.setQuestion("What?");
        question1.setAnswer("hmm");

        question2 = new Question();
        question2.setId(2);
        question2.setQuestion("How?");
        question2.setAnswer("...");

        question3 = new Question();
        question3.setId(3);
        question3.setQuestion("Where?");
        question3.setAnswer("whatever");

        questionList = Arrays.asList(question1, question2);

        question1_updated = new Question();
        question1_updated.setId(1);
        question1_updated.setQuestion("What? How?");
        question1_updated.setAnswer("hmm ...");
    }

    @Test
    void testAllQuestion() {

        when(questionRepository.findAll()).thenReturn(Flux.fromIterable(questionList));

        String document = """
        query {
            allQuestion {
                id, question, answer
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("allQuestion")
            .entityList(Question.class)
            .hasSize(2);
    }

    @Test
    void testAddQuestion() {

        when(questionRepository.save(any(Question.class))).thenReturn(Mono.just(question3));

        String document = """
        mutation{
            addQuestion(questionInput: {
                question:"Where?"
                answer: "whatever"
            }) {
                id, question, answer
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("addQuestion.question")
            .entity(String.class)
            .isEqualTo("Where?");

    }

    @Test
    void testUpdateQuestion() {

        when(questionRepository.findById(1)).thenReturn(Mono.just(question1));
        when(questionRepository.save(any(Question.class))).thenReturn(Mono.just(question1_updated));

        String document = """
        mutation UpdateQuestion {
            updateQuestion(question: {
                id: 1
                question:"What? How?"
                answer: "hmm ..."
            }) {
                id
                question
                answer
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("updateQuestion.answer")
            .entity(String.class)
            .isEqualTo("hmm ...");

        verify(questionRepository, times(1)).findById(1);
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void testDeleteQuestion() {
        when(questionRepository.findById(1)).thenReturn(Mono.just(question1));
        when(questionRepository.delete(question1)).thenReturn(Mono.empty());

        String document = """
        mutation {
            deleteQuestion(id:1) {
                id
                question
                answer
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("deleteQuestion.question")
            .entity(String.class)
            .isEqualTo("What?");

        verify(questionRepository, times(1)).findById(1);
        verify(questionRepository, times(1)).delete(question1);
    }

}

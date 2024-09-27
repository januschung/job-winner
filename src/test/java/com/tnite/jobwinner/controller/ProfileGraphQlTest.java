package com.tnite.jobwinner.controller;


import com.tnite.jobwinner.model.Profile;
import com.tnite.jobwinner.repo.ProfileRepository;
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
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureGraphQlTester
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
	"spring.r2dbc.url=r2dbc:h2:mem:///testdb-${random.uuid}",
	"spring.r2dbc.generate-unique-name=true",
})
@ExtendWith(MockitoExtension.class)
public class ProfileGraphQlTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    ProfileRepository profileRepository;

    private Profile profile;
    private Profile profile_updated;

    @BeforeEach
    void setUp() {
        profile = new Profile(1, null, null, null, null, null, null, null, null, null, null);
        profile_updated = new Profile(1, "John", "Doe", "123 Main St", "Apt 4", "City", "State", "12345", "john.doe", "johndoe", "www.johndoe.com");

    }

    @Test
	void testUpdateInterview() {

		when(profileRepository.findById(1)).thenReturn(Mono.just(profile));
        when(profileRepository.save(any(Profile.class))).thenReturn(Mono.just(profile_updated));

        String document = """
        mutation  {
            updateProfile(profile: {
                id: 1,
                firstName: "John",
                lastName: "Doe",
                addressStreet1: "123 Main St"
                addressStreet2: "Apt 4"
                addressCity: "City"
                addressState: "State"
                addressZip: "12345"
                linkedin: "john.doe"
                github: "johndoe"
                personalWebsite: "www.johndoe.com"
            }) {
                id
                firstName
                lastName
                addressStreet1
                addressStreet2
                addressCity
                addressState
                addressZip
                linkedin
                github
                personalWebsite
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("updateProfile.personalWebsite")
            .entity(String.class)
            .isEqualTo("www.johndoe.com");

		verify(profileRepository, times(1)).findById(1);
		verify(profileRepository, times(1)).save(any(Profile.class));
	}

    @Test
    void testGetProfile() {
        when(profileRepository.findById(1)).thenReturn(Mono.just(profile));

        String document = """
        query {
            profileById(id: 1) {
                id, firstName, lastName, addressStreet1, addressStreet2, addressCity, addressState, addressZip, linkedin, github, personalWebsite
            }
        }
        """;

        graphQlTester.document(document)
            .execute()
            .path("profileById")
            .entity(Profile.class)
            .isEqualTo(profile);

        verify(profileRepository, times(1)).findById(1);
    }
}

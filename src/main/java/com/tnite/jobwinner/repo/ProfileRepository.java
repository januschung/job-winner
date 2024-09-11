package com.tnite.jobwinner.repo;

import com.tnite.jobwinner.model.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, Integer>{

}

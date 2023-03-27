package com.tnite.jobwinner.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.tnite.jobwinner.model.Profile;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, Integer>{

}

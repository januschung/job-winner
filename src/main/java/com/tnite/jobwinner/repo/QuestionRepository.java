package com.tnite.jobwinner.repo;

import com.tnite.jobwinner.model.Question;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface QuestionRepository extends ReactiveCrudRepository<Question, Integer> {

}

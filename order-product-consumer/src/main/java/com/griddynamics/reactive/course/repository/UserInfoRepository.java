package com.griddynamics.reactive.course.repository;

import com.griddynamics.reactive.course.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends ReactiveMongoRepository<User, String> {
}

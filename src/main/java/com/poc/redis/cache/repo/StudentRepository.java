package com.poc.redis.cache.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.poc.redis.cache.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {

}

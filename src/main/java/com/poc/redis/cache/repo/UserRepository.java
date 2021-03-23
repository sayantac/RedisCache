package com.poc.redis.cache.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.poc.redis.cache.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

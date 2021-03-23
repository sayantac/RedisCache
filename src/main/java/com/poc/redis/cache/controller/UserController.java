package com.poc.redis.cache.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.redis.cache.entity.User;
import com.poc.redis.cache.repo.UserRepository;

@RestController
public class UserController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
	  this.userRepository = userRepository;
	}
	
	@Cacheable(value = "users", key = "#userId", unless = "#result.followers < 12000")
	@GetMapping("/user/{userId}")
	public User getUser(@PathVariable String userId) {
	  LOG.info("Getting user with ID {}.", userId);
	  return userRepository.findById(Long.valueOf(userId)).orElse(null);
	}
	
	@CachePut(value = "users", key = "#user.id")
	@PutMapping("/user")
	public User updatePersonByID(@RequestBody User user) {
	  userRepository.save(user);
	  return user;
	}
	
	@CacheEvict(value = "users", allEntries=true)
	@DeleteMapping("/user/{userId}")
	public void deleteUserByID(@PathVariable Long userId) {
	  LOG.info("deleting person with id {}", userId);
	  userRepository.deleteById(userId);
	}
	
	@GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

}

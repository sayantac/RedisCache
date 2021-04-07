package com.poc.redis.cache.repo.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.poc.redis.cache.repo.AuthenticationRepository;

@Repository
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
	
	final Logger logger = LoggerFactory.getLogger(AuthenticationRepositoryImpl.class);
	
	private HashOperations<String, String, Boolean> hashOperations;
	private ValueOperations<String, Boolean> valueOperations;
	/*
	 * private ZSetOperations<String, Boolean> zsetOperations; private
	 * SetOperations<String, Boolean> setOperations; private ListOperations<String,
	 * Boolean> listOperations;
	 */
	
	@Autowired
	RedisTemplate<String, Boolean> redisTemplate;
	
	public AuthenticationRepositoryImpl() {
		this.hashOperations = this.redisTemplate.opsForHash();
		this.valueOperations = this.redisTemplate.opsForValue();
		/*
		 * this.zsetOperations = this.redisTemplate.opsForZSet(); this.setOperations =
		 * this.redisTemplate.opsForSet(); this.listOperations =
		 * this.redisTemplate.opsForList();
		 */
	}

	@Override
	public void create(String hashKey, Boolean value) {	
		hashOperations.put("RedisHashAuth", hashKey, value);
        logger.info(String.format("Auth with ID %s saved", hashKey));
	}

	@Override
	public Boolean get(String hashKey) {
		return hashOperations.get("RedisHashAuth", hashKey);
	}

	@Override
	public Map<String, Boolean> getAll() {
		return hashOperations.entries("RedisHashAuth");
	}

	@Override
	public void update(String hashKey, Boolean value) {
		hashOperations.put("RedisHashAuth", hashKey, value);
        logger.info(String.format("Auth with ID %s updated", hashKey));
	}

	@Override
	public void delete(String hashKey) {
		hashOperations.delete("RedisHashAuth", hashKey);
        logger.info(String.format("Auth with ID %s deleted", hashKey));
	}

	@Override
	public void setValue(String key, Boolean value) {
		valueOperations.set(key, value);
		redisTemplate.expire(key, 5, TimeUnit.MINUTES);
	}
	
	@Override
	public Boolean getValue(String key) {
	    return valueOperations.get(key);
	}
}

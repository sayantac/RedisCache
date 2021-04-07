package com.poc.redis.cache.repo;

import java.util.Map;

public interface AuthenticationRepository {
	
	void create(String hashKey, Boolean value);
	Boolean get(String hashKey);
	Map<String, Boolean> getAll();
	void update(String hashKey, Boolean value);
	void delete(String hashKey);
	void setValue(String key, Boolean value);
	Boolean getValue(String key);

}

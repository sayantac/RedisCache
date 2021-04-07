package com.poc.redis.cache.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.poc.redis.cache.repo.AuthenticationRepository;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	AuthenticationRepository authenticationRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
				  new CachedBodyHttpServletRequest(request);
		
		String requestBody = cachedBodyHttpServletRequest.getReader().lines().collect(Collectors.joining("\n"));
		JSONObject json = new JSONObject(requestBody); 
		
		LOG.info("Follower of " + json.getString("name") + " with id:: " + 
		json.getString("id") + " is " + json.getInt("followers"));
		
		authenticationRepository.create("60096310e52d3e000169a50c|schakrabor282", true);
		
		filterChain.doFilter(cachedBodyHttpServletRequest, response);

	}

}

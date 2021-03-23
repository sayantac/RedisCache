package com.poc.redis.cache.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.redis.cache.entity.Student;
import com.poc.redis.cache.repo.StudentRepository;

@RestController
public class StudentController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final StudentRepository studentRepository;

	@Autowired
	public StudentController(StudentRepository studentRepository) {
	  this.studentRepository = studentRepository;
	}
	
	@GetMapping("/student/{studentId}")
	public Student getStudentById(@PathVariable String studentId) {
	  LOG.info("Getting student with ID {}.", studentId);
	  return studentRepository.findById(studentId).orElse(null);
	}
	
	@GetMapping("/student")
	public List<Student> getAllStudents() {
	  LOG.info("Getting all students");  
	  List<Student> students = new ArrayList<>();
	  studentRepository.findAll().forEach(students::add);  
	  return students;
	}
	
	@PostMapping("/student")
	public Student saveStudent(@RequestBody Student student) {	
	  return studentRepository.save(student);
	}
	
	@PutMapping("/student")
	public Student updateStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	@DeleteMapping("/student/{studentId}")
	public void deleteUserByID(@PathVariable String studentId) {
	  LOG.info("deleting student with id {}", studentId);
	  studentRepository.deleteById(studentId);
	}

}

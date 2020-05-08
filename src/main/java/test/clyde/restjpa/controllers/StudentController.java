package test.clyde.restjpa.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import test.clyde.restjpa.config.StudentConfig;
import test.clyde.restjpa.entities.Student;
import test.clyde.restjpa.exceptions.StudentNotFoundException;
import test.clyde.restjpa.repositories.StudentRepository;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentConfig config;
	
	private List<Integer> lots;
	
	@GetMapping("/students")
	public List<Student> retrieveAllStudents() {
		return studentRepository.findAll();
	}
	
	@PostConstruct
	private void init() {
		lots = IntStream.range(0, config.getRange()).boxed().collect(Collectors.toList());
	}
	
	@GetMapping("/lots")
	public Page<Integer> getLots(Pageable pageable) {
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > lots.size() ? lots.size() : (start + pageable.getPageSize());
		return new PageImpl<Integer>(lots.subList(start, end), pageable, lots.size());
	}

	@GetMapping("/students/{id}")
	public Student retrieveStudent(@PathVariable long id) {
		Optional<Student> student = studentRepository.findById(id);

		if (!student.isPresent())
			throw new StudentNotFoundException(id);

		return student.get();
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable long id) {
		studentRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/students")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		Student savedStudent = studentRepository.save(student);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable long id) {

		Optional<Student> studentOptional = studentRepository.findById(id);

		if (!studentOptional.isPresent())
			throw new StudentNotFoundException(id);

		student.setId(id);
		
		studentRepository.save(student);

		return ResponseEntity.noContent().build();
	}
}

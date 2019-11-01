package test.clyde.restjpa;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import test.clyde.restjpa.controllers.StudentController;
import test.clyde.restjpa.entities.Student;
import test.clyde.restjpa.entities.StudentType;
import test.clyde.restjpa.repositories.StudentRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
public class RestJpaApplicationTests {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private StudentRepository studentRepository;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testGetAll() throws Exception {
		List<Student> all = getAll();
		given(this.studentRepository.findAll()).willReturn(all);
		
		mvc.perform(get("/students")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.length()", is(all.size())))
			      .andExpect(jsonPath("$[0].name", is(all.get(0).getName())))
			      .andExpect(jsonPath("$[1].passportNumber", is(all.get(1).getPassportNumber())));
	}
	
	@Test
	public void testGetOne() throws Exception {
		Student s = getAll().get(1);
		given(this.studentRepository.findById(s.getId())).willReturn(Optional.of(s));
		
		mvc.perform(get("/students/" + s.getId())
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.name", is(s.getName())))
			      .andExpect(jsonPath("$.passportNumber", is(s.getPassportNumber())));
	}
	
	private List<Student> getAll() {
		Student s1 = new Student(1L, "Student1", "ABC", StudentType.UnderGrad);
		Student s2 = new Student(2L, "Student2", "123", StudentType.PostGrad);
		List<Student> all = new ArrayList<>();
		all.add(s1);
		all.add(s2);
		return all;
	}
}

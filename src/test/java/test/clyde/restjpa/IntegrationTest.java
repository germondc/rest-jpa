package test.clyde.restjpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import test.clyde.restjpa.entities.Student;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RestJpaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	private RestTemplate restTemplate = new RestTemplate();
	
	@LocalServerPort
	private int port;

	@Test
	public void whenUsingRestTemplate_thenSucceed() {
		try {
			ResponseEntity<Student> course = restTemplate.getForEntity(getUrl(), Student.class);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
	}
	
	private String getUrl() {
		return "http://localhost:" + port + "/students/1";
	}
}

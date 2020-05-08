package test.clyde.restjpa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.student")
public class StudentConfig {

	/**
	 * The size of the number of students to simulate
	 */
	private int range;

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
    
}

package at.nonblocking.fhir_server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class FhirServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(FhirServer1Application.class, args);
	}

}

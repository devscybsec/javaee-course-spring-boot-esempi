package it.cybsec.spring;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("it.cybsec")
@ComponentScan("it.cybsec")
public class SpringBootEsempioApplication {
	
	@PostConstruct
	void started() {
		System.out.println(TimeZone.getDefault().toString());
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEsempioApplication.class, args);
	}

}

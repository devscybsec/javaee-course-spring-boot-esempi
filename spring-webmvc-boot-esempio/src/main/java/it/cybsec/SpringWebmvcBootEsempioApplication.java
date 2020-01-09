package it.cybsec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import it.cybsec.model.Impiegato;

@SpringBootApplication
public class SpringWebmvcBootEsempioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebmvcBootEsempioApplication.class, args);
	}
	
	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer()
	{
	    return RepositoryRestConfigurer.withConfig(config -> {
	        config.exposeIdsFor(Impiegato.class);
//	        ADD OTHER CLASS HERE
	    });
	}

}

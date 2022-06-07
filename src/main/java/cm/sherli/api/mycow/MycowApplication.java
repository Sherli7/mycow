package cm.sherli.api.mycow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
 * @author 
 */
@SpringBootApplication
@EnableJpaAuditing
public class MycowApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
    SpringApplication.run(MycowApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(MycowApplication.class);
	}
	
}

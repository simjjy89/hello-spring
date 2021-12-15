package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		//SpringApplication.run(HelloSpringApplication.class, args);

		ConfigurableApplicationContext ctx =  SpringApplication.run(HelloSpringApplication.class, args);

		ctx.close();

	}

}

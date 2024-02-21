package site.junyo.minheegame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MinheeGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinheeGameApplication.class, args);
	}
}

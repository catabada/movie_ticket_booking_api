package vn.edu.hcmuaf.fit.movie_ticket_booking_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_role.AppRoleRepository;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
@EnableAsync
public class MovieTicketBookingApiApplication {
	private static AppRoleRepository appRoleRepository;

	@Autowired
	public void setAppRoleRepository(AppRoleRepository appRoleRepository) {
		MovieTicketBookingApiApplication.appRoleRepository = appRoleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieTicketBookingApiApplication.class, args);
//		appRoleRepository.save(AppRole.builder().name(RoleConstant.ROLE_MEMBER).build());
//		appRoleRepository.save(AppRole.builder().name(RoleConstant.ROLE_ADMIN).build());
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			log.info("Let's inspect the beans provided by Spring Boot:");
			log.info("----------------------------------------------");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				log.info(beanName);
			}
			log.info("----------------------------------------------");

		};
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

package fr.polytech.ig5.mnm.gatewayms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class GatewayMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayMsApplication.class, args);
	}

	@Autowired
	private Environment env;


	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		String userMsURI  = env.getProperty("USER_MS_URI");
		String offerMsURI  = env.getProperty("OFFER_MS_URI");
		String recruitmentMsURI  = env.getProperty("RECRUITMENT_MS_URI");

		return builder.routes()
				.route("r11", r -> r.path("/workers/**")
						.and()
						.uri(userMsURI))

				.route("r12", r -> r.path("/companies/**")
						.and()
						.uri(userMsURI))

				.route("r21", r -> r.path("/offers/**")
						.and()
						.uri(offerMsURI))
				.route("r22", r -> r.path("/applications/**")
						.and()
						.uri(offerMsURI))
				.route("r23", r -> r.path("/criterias/**")
						.and()
						.uri(offerMsURI))

				.route("r31", r -> r.path("/works/**")
						.and()
						.uri(recruitmentMsURI))
				.build();
	}

}

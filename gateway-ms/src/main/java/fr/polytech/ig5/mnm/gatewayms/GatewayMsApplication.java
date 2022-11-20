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
				.route("r1", r -> r.path("/users/**")
						.and()
						.uri(userMsURI))

				.route("r2", r -> r.path("/offers/**")
						.and()
						.uri(offerMsURI))

				.route("r3", r -> r.path("/recruitment/**")
						.and()
						.uri(recruitmentMsURI))
				.build();
	}

}

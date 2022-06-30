package pl.pawc.jtru;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.pawc.jtru.auth.DatabaseUserDetailsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
@EnableWebSecurity
public class JtruApplication extends WebSecurityConfigurerAdapter{

	@Value("${bcryptWorkFactor}")
	String bcryptWorkFactor;

	@Value("${frontOrigin}")
	String frontOrigin;

	private final DatabaseUserDetailsService databaseUserDetailsService;

	public static void main(String[] args) {
		SpringApplication.run(JtruApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{

		http.cors()
			.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "/register")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic().authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
			.and().requestCache().requestCache(getHttpSessionRequestCache());
	}

	private class NoPopupBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
							 AuthenticationException authException) throws IOException, ServletException {

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		}

	}

	public HttpSessionRequestCache getHttpSessionRequestCache()
	{
		HttpSessionRequestCache httpSessionRequestCache = new HttpSessionRequestCache();
		httpSessionRequestCache.setCreateSessionAllowed(false);
		return httpSessionRequestCache;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		int strength;
		try{
			strength = Integer.parseInt(bcryptWorkFactor);
		}
		catch(NumberFormatException e){
			strength = 10;
		}
		return new BCryptPasswordEncoder(strength);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		config.setAllowedOrigins(Arrays.asList(frontOrigin));
		config.setAllowCredentials(true);

		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider provider =
				new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(databaseUserDetailsService);
		//provider.setUserDetailsPasswordService(databaseUserDetailsPasswordService);

		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

}
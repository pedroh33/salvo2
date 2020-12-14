package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}
		@Bean
		public PasswordEncoder passwordEncoder() {
			return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}




	@Bean
	public CommandLineRunner initData(
			PlayerRepository player_rep,
			GameRepository game_rep,
			GamePlayerRepository gp_rep,
			ShipRepository ship_rep,
			SalvoRepository salvo_rep,
			ScoreRepository scoreRepository
	){
		return (args) -> {
			Player A = new Player("Angela", "angie@proyecto.acc",passwordEncoder().encode("123"));
			Player B = new Player("Brian", "brian@proyecto.acc",passwordEncoder().encode("123"));
			Player C = new Player("Carlos", "charles@proyecto.acc",passwordEncoder().encode("123"));
			Player D = new Player("Daniela", "dani@proyecto.acc",passwordEncoder().encode("123"));

			Date current = new Date();
			Game G1 = new Game(current); current = Date.from(current.toInstant().plusSeconds(3600));
			Game G2 = new Game(current); current = Date.from(current.toInstant().plusSeconds(3600));
			Game G3 = new Game(current); current = Date.from(current.toInstant().plusSeconds(3600));
			Game G4 = new Game(current); current = Date.from(current.toInstant().plusSeconds(3600));
			Game G5 = new Game(current); current = Date.from(current.toInstant().plusSeconds(3600));
			Game G6 = new Game(current); current = Date.from(current.toInstant().plusSeconds(3600));
			Game G7 = new Game(current); current = Date.from(current.toInstant().plusSeconds(3600));
			Game G8 = new Game(current); current = Date.from(current.toInstant().plusSeconds(3600));

			GamePlayer GP1 = new GamePlayer(A, G1); GamePlayer GP2 = new GamePlayer(B, G1);
			GamePlayer GP3 = new GamePlayer(A, G2); GamePlayer GP4 = new GamePlayer(B, G2);
			GamePlayer GP5 = new GamePlayer(B, G3); GamePlayer GP6 = new GamePlayer(D, G3);
			GamePlayer GP7 = new GamePlayer(B, G4); GamePlayer GP8 = new GamePlayer(A, G4);
			GamePlayer GP9 = new GamePlayer(D, G5); GamePlayer GP10 = new GamePlayer(A, G5);
			GamePlayer GP11 = new GamePlayer(C, G6); GamePlayer GP12 = new GamePlayer();
			GamePlayer GP13 = new GamePlayer(D, G7); GamePlayer GP14 = new GamePlayer();
			GamePlayer GP15 = new GamePlayer(C, G8); GamePlayer GP16 = new GamePlayer(D, G8);







			/*
			Ship SA = new Ship("Acorazado", GP1, Arrays.asList("A1", "A2", "A3"));
			Ship SB = new Ship("Mercante", GP1, Arrays.asList("A2", "D2"));
			Ship SC = new Ship("Galeon", GP1, Arrays.asList("H3", "H4", "H5", "H6"));
			Ship SD = new Ship("Acorazado", GP2, Arrays.asList("A6", "A7", "A8"));


			/
			Ship S1 = new Ship("carrier", Arrays.asList("H2", "H3", "H4", "H5", "H6"), GP1);
			Ship S2 = new Ship("submarine", Arrays.asList("E1", "F1", "G1"),GP1);
			Ship S3 = new Ship("patrolboat", Arrays.asList("B4", "B5"),GP1);
			Ship S4 = new Ship("destroyer",Arrays.asList("B5", "C5", "D5"),GP2);
			Ship S5 = new Ship("patrolboat",Arrays.asList("F1", "F2"),GP2);

			Ship S6 = new Ship("destroyer",Arrays.asList("B5", "C5", "D5"),GP3);
			Ship S7 = new Ship("patrolboat",Arrays.asList("C6", "C7"),GP3);
			Ship S8 = new Ship("submarine",Arrays.asList("A2", "A3", "A4"),GP4);
			Ship S9 = new Ship("patrolboat",Arrays.asList("G6", "H6"),GP4);

			Ship S10 = new Ship("destroyer",Arrays.asList("B5", "C5", "D5"),GP5);
			Ship S11 = new Ship("patrolboat",Arrays.asList("C6", "C7"),GP5);
			Ship S12 = new Ship("submarine",Arrays.asList("A2", "A3", "A4"),GP6);
			Ship S13 = new Ship("patrolboat",Arrays.asList("G6", "H6"),GP6);

			Ship S14 = new Ship("destroyer",Arrays.asList("B5", "C5", "D5"),GP7);
			Ship S15 = new Ship("patrolboat",Arrays.asList("C6", "C7"),GP7);
			Ship S16 = new Ship("submarine",Arrays.asList("A2", "A3", "A4"),GP8);
			Ship S17 = new Ship("patrolboat",Arrays.asList("G6", "H6"),GP8);

			Ship S18 = new Ship("destroyer",Arrays.asList("B5", "C5", "D5"),GP9);
			Ship S19 = new Ship("patrolboat", Arrays.asList("C6", "C7"),GP9);
			Ship S20 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"),GP10);
			Ship S21 = new Ship("atrolboat",Arrays.asList("G6", "H6"),GP10);

			Ship S22 = new Ship("destroyer",Arrays.asList("B5", "C5", "D5"),GP11);
			Ship S23 = new Ship("patrolboat", Arrays.asList("C6", "C7"),GP11);

			Ship S24 = new Ship("destroyer",Arrays.asList("B5", "C5", "D5"),GP15);
			Ship S25 = new Ship("patrolboat",Arrays.asList("C6", "C7"),GP15);
			Ship S26 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"),GP16);
			Ship S27 = new Ship("patrolboat",Arrays.asList("G6", "H6"),GP16);


			Salvo T1 = new Salvo(GP1, 1, Arrays.asList("B5", "C5", "F1"));
			Salvo T2 = new Salvo(GP1, 2, Arrays.asList("F2", "D5"));
			Salvo T3 = new Salvo(GP2, 1, Arrays.asList("B4", "B5", "B6"));
			Salvo T4 = new Salvo(GP2, 2, Arrays.asList("E1", "H3", "A2"));

			Salvo T5 = new Salvo(GP3, 1, Arrays.asList("A2", "A4", "G6"));
			Salvo T6 = new Salvo(GP3, 2, Arrays.asList("A3", "H6"));
			Salvo T7 = new Salvo(GP4, 1, Arrays.asList("B5", "D5", "C7"));
			Salvo T8 = new Salvo(GP4, 2, Arrays.asList("C5", "C6"));

			Salvo T9 = new Salvo(GP5, 1, Arrays.asList("G6", "H6", "A4"));
			Salvo T10 = new Salvo(GP5, 1, Arrays.asList("A2", "A3", "D8"));
			Salvo T11 = new Salvo(GP6, 1, Arrays.asList("H1", "H2", "H3"));
			Salvo T12 = new Salvo(GP6, 1, Arrays.asList("E1", "F2", "G3"));

			Salvo T13 = new Salvo(GP7, 1, Arrays.asList("A3", "A4", "F7"));
			Salvo T14 = new Salvo(GP7, 2, Arrays.asList("A2", "G6", "H6"));
			Salvo T15 = new Salvo(GP8, 1, Arrays.asList("B5", "C6", "H1"));
			Salvo T16 = new Salvo(GP8, 2, Arrays.asList("C5", "C7", "D5"));

			Salvo T17 = new Salvo(GP9, 1, Arrays.asList("A1", "A2", "A3"));
			Salvo T18 = new Salvo(GP9, 2, Arrays.asList("G6", "G7", "G8"));
			Salvo T19 = new Salvo(GP10, 1, Arrays.asList("B5", "B6", "C7"));
			Salvo T20 = new Salvo(GP10, 2, Arrays.asList("C6", "D6", "E6"));
			Salvo T21 = new Salvo(GP10, 3, Arrays.asList("H1", "H8"));

			GP1.addSalvo(T1);
			GP3.addSalvo(T3);
*/
			player_rep.saveAll(Arrays.asList(A, B, C, D));
			game_rep.saveAll(Arrays.asList(G1, G2, G3, G4, G5, G6, G7, G8));

			gp_rep.saveAll(Arrays.asList(
					GP1,  GP2,  GP3,  GP4,  GP5,  GP6,  GP7,  GP8,  GP9, GP10,
					GP11, GP13, GP15, GP16));

			Ship S1 = new Ship("carrier", Arrays.asList("H2", "H3", "H4", "H5", "H6"), GP1);
			Ship S2 = new Ship("submarine", Arrays.asList("E1", "F1", "G1"),GP2);
			Ship S3 = new Ship("patrolboat", Arrays.asList("B4", "B5"),GP1);
			Ship S4 = new Ship("destroyer",Arrays.asList("B5", "C5", "D5"),GP2);
			Ship S5 = new Ship("patrolboat",Arrays.asList("F1", "F2"),GP2);

			ship_rep.saveAll(Arrays.asList(S1, S2, S3, S4, S5));

			Score score1 = new Score(A,G1,1.0, LocalDateTime.now());








			// player_rep.save(A); player_rep.save(B); player_rep.save(C);


			ship_rep.saveAll(Arrays.asList(
					S1,  S2,  S3,  S4,  S5));
			/*salvo_rep.saveAll(Arrays.asList(

			scoreRepository.save(score1);
		*/
		};

	}


}
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playereRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playereRepository.findByEmail(inputName);
			if (player != null) {
				return new User(player.getEmail(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/web/**").permitAll()
				.antMatchers("/api/game_view/*").hasAuthority("USER")
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/api/games").permitAll();

		http.formLogin()
				.usernameParameter("name")
				.passwordParameter("pwd")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();
		http.headers().frameOptions().disable();


		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}

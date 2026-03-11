package fincance_manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User; // Import que faltava
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults; // Import importante

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilitado para o Postman funcionar no POST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register").permitAll() // Libera o cadastro sem login
                .anyRequest().authenticated()
            )
            
            .formLogin(withDefaults()) // Tela de login do navegador
            .httpBasic(withDefaults()); // Autenticação básica para o Postman
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // Aqui usamos o encoder que definimos acima para não dar erro de senha
        UserDetails user = User.builder()
            .username("nicolas")
            .password(encoder.encode("123")) // Criptografa a senha "123"
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
}
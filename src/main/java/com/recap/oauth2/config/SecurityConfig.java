package com.recap.oauth2.config;

import com.recap.domain.user.repository.UserRepository;
import com.recap.global.jwt.CookieUtil;
import com.recap.global.jwt.JwtFilter;
import com.recap.global.jwt.JwtUtil;
import com.recap.global.jwt.LoginFilter;
import com.recap.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable); // csrf 공격 방지

        //WebMvcConfig 설정에 따름
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        //FormLogin, BasicHttp 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Unauthorized request\"}");
                        })
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/oauth2/**","/login", "/swagger-ui/**",    // Swagger UI 관련 경로
                                "/v3/api-docs/**","/csrf-token", "/posts/**", "/posts", "/universities", "/users", "/tests").permitAll()
                        .requestMatchers("/task/**").hasAnyRole("MEMBER", "MANAGER", "VIEWER")
                        .anyRequest().authenticated()
                );
//        http
//                .addFilterAt(new JwtFilter(jwtUtil, userDetailsService), LoginFilter.class)
//                .addFilterBefore(
//                new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, cookieUtil, userRepository),
//                UsernamePasswordAuthenticationFilter.class);

//        http
//                .addFilterAt(new CustomLogoutFilter(jwtUtil), LogoutFilter.class);
        http
                .addFilterBefore(new JwtFilter(jwtUtil, userDetailsService), LoginFilter.class) // 로그인 필터 전에 jwt 토큰 확인, 없으면 통과
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, cookieUtil, userRepository), UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:63342"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.addExposedHeader("access");
        configuration.addExposedHeader("refresh");
        configuration.setAllowCredentials(true); // 인증 정보 포함, 단 이게 true면 allowOrigins에 *은 안됨.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

// dummy change
// dummy change

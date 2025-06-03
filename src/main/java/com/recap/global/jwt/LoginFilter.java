package com.recap.global.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.recap.domain.user.entity.User;
import com.recap.domain.user.repository.UserRepository;
import com.recap.global.dto.LoginRequest;
import com.recap.global.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final UserRepository userRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CookieUtil cookieUtil, UserRepository userRepository){
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.userRepository = userRepository;
        super.setAuthenticationManager(authenticationManager);
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Attempting authentication...");
        try {
            String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            LoginRequest loginRequest = new ObjectMapper().readValue(messageBody, LoginRequest.class);

            Optional<User> user = userRepository.findById(1L);
            log.info("");
//
//            if (user.get().getSocial() != null){
//                throw new RuntimeException(user.get().getSocial() + "계정으로 가입된 회원입니다.");
//            }

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.userId(), loginRequest.password(), null);

            return getAuthenticationManager().authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read request body", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication)
            throws IOException, ServletException {
        log.info("Authentication successful...");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getEmail();
        try {
            response.addHeader("access", jwtUtil.createAccess(email));
        } catch (Exception e) {
            log.error("Error generating JWT: ", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.addCookie(CookieUtil.createCookie("refresh", jwtUtil.createRefresh(email)));
        response.setStatus(HttpStatus.OK.value());
    }

}
// dummy change
// dummy change

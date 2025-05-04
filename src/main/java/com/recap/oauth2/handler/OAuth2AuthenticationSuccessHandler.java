package com.recap.oauth2.handler;

import com.recap.global.jwt.CookieUtil;
import com.recap.global.jwt.JwtUtil;
import com.recap.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.recap.oauth2.service.OAuth2UserPrincipal;
import com.recap.oauth2.user.OAuth2UserUnlinkManager;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.recap.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static com.recap.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;
    private final JwtUtil jwtUtil;

    private final OAuth2AuthorizedClientService authorizedClientService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String code = request.getParameter("code");
        System.out.println("Authorization Code: " + code);


        String targetUrl = determineTargetUrl(request, response, authentication);

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);


        if (principal != null) {
            String accessToken = jwtUtil.createAccess(principal.getUserInfo().getEmail());
            String refreshToken = jwtUtil.createRefresh(principal.getUserInfo().getEmail());
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

            log.info("OAuth2AuthorizedToken: {}", client.getAccessToken().getTokenValue());

            String googleAccessToken = client.getAccessToken().getTokenValue();
            CookieUtil.addCookie(response, "access_token", accessToken, 3600);
            CookieUtil.addCookie(response, "google_oauth_token", googleAccessToken, 3600);
            CookieUtil.addCookie(response, "refresh_token", refreshToken, 86400);

            log.info("✅ OAuth 로그인 성공! 쿠키 설정 완료! 리디렉션 실행: {}", targetUrl);
        }

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to {}", targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        log.info("redirectUrl: {}", redirectUri);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        log.info("targetUrl: {}", targetUrl);
        String mode = CookieUtil.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        log.info("mode: {}", mode);

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);
        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        log.info("🔹 로그인 성공: mode={}, email={}", mode, principal.getUserInfo().getEmail());

        if ("login".equalsIgnoreCase(mode)) {

            String access = jwtUtil.createAccess(principal.getUserInfo().getEmail());
            String refresh = jwtUtil.createRefresh(principal.getUserInfo().getEmail());

            CookieUtil.addCookie(response, "access_token", access, 3600);
            CookieUtil.addCookie(response, "refresh_token", refresh, 86400);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", "Login failed")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
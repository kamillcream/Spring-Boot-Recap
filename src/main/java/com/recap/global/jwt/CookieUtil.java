package com.recap.global.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Component
public class CookieUtil {
    private static final int COOKIE_EXPIRE_TIME = 30 * 60; // 30분

    public static Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRE_TIME);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);  // HTTPS 요청에만 secure 설정
        cookie.setAttribute("SameSite", "Strict");
        return cookie;
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        // cookie.setDomain("");
        cookie.setHttpOnly(false);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public ResponseCookie createResponseCookie(String refreshToken){
        return ResponseCookie.from("refresh", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict") // CSRF 방어
                .path("/")      // 모든 경로에서 유효
                .maxAge(COOKIE_EXPIRE_TIME) // 7일 유지
                .build();
    }

    public Cookie createCsrfCookie(HttpServletRequest request, HttpServletResponse response){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        Cookie csrfCookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
        csrfCookie.setSecure(false);
        csrfCookie.setPath("/");
        response.addCookie(csrfCookie);

        return csrfCookie;
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Optional.ofNullable(request.getCookies())
                .ifPresent(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> name.equals(cookie.getName()))
                        .forEach(cookie -> {
                            cookie.setValue("");  // 필요하지 않음, setMaxAge(0)으로 충분함
                            cookie.setPath("/");
                            cookie.setMaxAge(0);  // 쿠키 삭제
                            cookie.setHttpOnly(true);
                            cookie.setSecure(request.isSecure());
                            response.addCookie(cookie);
                        }));
    }
    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.getValue())));
    }

    public static Optional<String> getRefreshTokenFromRequest(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

}
// dummy change

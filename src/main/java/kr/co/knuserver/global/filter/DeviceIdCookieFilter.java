package kr.co.knuserver.global.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import kr.co.knuserver.application.booth.DeviceIdGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeviceIdCookieFilter implements Filter {

    public static final String DEVICE_ID_ATTRIBUTE = "deviceId";
    private static final String COOKIE_NAME = "deviceId";

    private final int cookieMaxAge;
    private final DeviceIdGenerator deviceIdGenerator;

    public DeviceIdCookieFilter(int cookieMaxAge, DeviceIdGenerator deviceIdGenerator) {
        this.cookieMaxAge = cookieMaxAge;
        this.deviceIdGenerator = deviceIdGenerator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!isLikesPath(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        String deviceId = resolveDeviceId(httpRequest);
        if (deviceId == null) {
            deviceId = deviceIdGenerator.generate();
            Cookie cookie = new Cookie(COOKIE_NAME, deviceId);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(cookieMaxAge);
            httpResponse.addCookie(cookie);
            log.debug("[DeviceId] 신규 발급 ip={} deviceId={}",
                httpRequest.getAttribute(ClientIpFilter.CLIENT_IP_ATTRIBUTE), deviceId);
        }

        httpRequest.setAttribute(DEVICE_ID_ATTRIBUTE, deviceId);
        chain.doFilter(request, response);
    }

    private boolean isLikesPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.matches(".*/booths/[^/]+/likes$");
    }

    private String resolveDeviceId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
            .filter(c -> COOKIE_NAME.equals(c.getName()))
            .map(Cookie::getValue)
            .findFirst()
            .orElse(null);
    }
}

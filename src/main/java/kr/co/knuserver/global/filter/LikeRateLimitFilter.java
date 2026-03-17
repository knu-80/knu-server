package kr.co.knuserver.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import kr.co.knuserver.global.ratelimit.LikeRateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Slf4j
@RequiredArgsConstructor
public class LikeRateLimitFilter implements Filter {

    private final LikeRateLimiter likeRateLimiter;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!isLikesPath(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        String clientIp = (String) httpRequest.getAttribute(ClientIpFilter.CLIENT_IP_ATTRIBUTE);
        String deviceId = (String) httpRequest.getAttribute(DeviceIdCookieFilter.DEVICE_ID_ATTRIBUTE);

        if (!likeRateLimiter.isAllowed(clientIp, deviceId)) {
            log.warn("[LikeRateLimit] 한도 초과 ip={} deviceId={}", clientIp, deviceId);
            sendTooManyRequestsResponse(httpResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isLikesPath(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod())
            && request.getRequestURI().matches(".*/booths/[^/]+/likes$");
    }

    private void sendTooManyRequestsResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
            objectMapper.writeValueAsString(Map.of("result", "TOO_MANY_REQUESTS"))
        );
    }
}

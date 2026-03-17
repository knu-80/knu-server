package kr.co.knuserver.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.knuserver.application.booth.DeviceIdGenerator;
import kr.co.knuserver.global.filter.ClientIpFilter;
import kr.co.knuserver.global.filter.DeviceIdCookieFilter;
import kr.co.knuserver.global.filter.LikeRateLimitFilter;
import kr.co.knuserver.global.ratelimit.LikeRateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    @Value("${device-id.cookie.max-age}")
    private int cookieMaxAge;

    private final DeviceIdGenerator deviceIdGenerator;
    private final LikeRateLimiter likeRateLimiter;
    private final ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean<ClientIpFilter> clientIpFilter() {
        FilterRegistrationBean<ClientIpFilter> bean = new FilterRegistrationBean<>(new ClientIpFilter());
        bean.addUrlPatterns("/api/v1/*");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<DeviceIdCookieFilter> deviceIdCookieFilter() {
        DeviceIdCookieFilter filter = new DeviceIdCookieFilter(cookieMaxAge, deviceIdGenerator);
        FilterRegistrationBean<DeviceIdCookieFilter> bean = new FilterRegistrationBean<>(filter);
        bean.addUrlPatterns("/api/v1/booths/*");
        bean.setOrder(2);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<LikeRateLimitFilter> likeRateLimitFilter() {
        LikeRateLimitFilter filter = new LikeRateLimitFilter(likeRateLimiter, objectMapper);
        FilterRegistrationBean<LikeRateLimitFilter> bean = new FilterRegistrationBean<>(filter);
        bean.addUrlPatterns("/api/v1/booths/*");
        bean.setOrder(3);
        return bean;
    }
}

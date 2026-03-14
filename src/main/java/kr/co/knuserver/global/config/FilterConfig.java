package kr.co.knuserver.global.config;

import kr.co.knuserver.application.booth.DeviceIdGenerator;
import kr.co.knuserver.global.filter.DeviceIdCookieFilter;
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

    @Bean
    public FilterRegistrationBean<DeviceIdCookieFilter> deviceIdCookieFilter() {
        DeviceIdCookieFilter filter = new DeviceIdCookieFilter(cookieMaxAge, deviceIdGenerator);
        FilterRegistrationBean<DeviceIdCookieFilter> bean = new FilterRegistrationBean<>(filter);
        bean.addUrlPatterns("/api/v1/booths/*");
        return bean;
    }
}

package kr.co.knuserver.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "like")
public record LikeProperties(
    RateLimit rateLimit,
    DoubleEvent doubleEvent
) {

    public record RateLimit(
        long ttlSeconds,
        long maxLikes
    ) {}

    public record DoubleEvent(
        boolean enabled,
        String startTime,
        String endTime
    ) {}
}

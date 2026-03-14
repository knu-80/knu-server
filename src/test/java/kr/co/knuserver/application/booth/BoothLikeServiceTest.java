package kr.co.knuserver.application.booth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

@ExtendWith(MockitoExtension.class)
class BoothLikeServiceTest {

    @InjectMocks
    private BoothLikeService boothLikeService;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private ZSetOperations<String, String> zSetOperations;

    @BeforeEach
    void setUp() {
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);
    }

    @Test
    @DisplayName("rate limit 미적용 상태에서 좋아요 시 누적 카운트를 반환한다")
    void like_성공() {
        // given
        Long boothId = 1L;
        String deviceId = "device-uuid-1234";
        given(valueOperations.setIfAbsent(anyString(), eq("1"), any(Duration.class))).willReturn(true);
        given(zSetOperations.incrementScore(eq("like:ranking"), eq("1"), eq(1.0))).willReturn(5.0);

        // when
        long count = boothLikeService.like(boothId, deviceId, "127.0.0.1");

        // then
        assertThat(count).isEqualTo(5L);
        verify(zSetOperations).incrementScore("like:ranking", "1", 1.0);
    }

    @Test
    @DisplayName("1초 이내 재요청 시 TOO_MANY_REQUESTS 예외가 발생한다")
    void like_레이트리밋_초과() {
        // given
        Long boothId = 1L;
        String deviceId = "device-uuid-1234";
        given(valueOperations.setIfAbsent(anyString(), eq("1"), any(Duration.class))).willReturn(false);

        // when & then
        assertThatThrownBy(() -> boothLikeService.like(boothId, deviceId, "127.0.0.1"))
            .isInstanceOf(BusinessException.class)
            .satisfies(e -> assertThat(((BusinessException) e).getErrorCode())
                .isEqualTo(BusinessErrorCode.TOO_MANY_REQUESTS));

        verify(zSetOperations, never()).incrementScore(anyString(), anyString(), anyDouble());
    }

    @Test
    @DisplayName("incrementScore가 null을 반환하면 0을 반환한다")
    void like_incrementScore_null이면_0반환() {
        // given
        Long boothId = 1L;
        String deviceId = "device-uuid-1234";
        given(valueOperations.setIfAbsent(anyString(), eq("1"), any(Duration.class))).willReturn(true);
        given(zSetOperations.incrementScore(anyString(), anyString(), anyDouble())).willReturn(null);

        // when
        long count = boothLikeService.like(boothId, deviceId, "127.0.0.1");

        // then
        assertThat(count).isZero();
    }

    @Test
    @DisplayName("좋아요 수 조회 시 Redis score를 long으로 변환해 반환한다")
    void getLikeCount_정상반환() {
        // given
        Long boothId = 1L;
        given(zSetOperations.score(eq("like:ranking"), eq("1"))).willReturn(42.0);

        // when
        long count = boothLikeService.getLikeCount(boothId);

        // then
        assertThat(count).isEqualTo(42L);
    }

    @Test
    @DisplayName("Redis에 score가 없으면 0을 반환한다")
    void getLikeCount_score없으면_0반환() {
        // given
        Long boothId = 1L;
        given(zSetOperations.score(eq("like:ranking"), eq("1"))).willReturn(null);

        // when
        long count = boothLikeService.getLikeCount(boothId);

        // then
        assertThat(count).isZero();
    }
}

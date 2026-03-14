package kr.co.knuserver.application.booth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Set;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothDivision;
import kr.co.knuserver.domain.booth.repository.BoothImageRepository;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.presentation.booth.dto.BoothRankingResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

@ExtendWith(MockitoExtension.class)
class BoothQueryServiceTest {

    @InjectMocks
    private BoothQueryService boothQueryService;

    @Mock
    private BoothRepository boothRepository;

    @Mock
    private BoothImageRepository boothImageRepository;

    @Mock
    private BoothLikeService boothLikeService;

    @Test
    @DisplayName("Redis 랭킹이 비어있으면 빈 리스트를 반환한다")
    void getBoothRanking_빈랭킹() {
        // given
        given(boothLikeService.getRanking()).willReturn(Set.of());

        // when
        List<BoothRankingResponseDto> result = boothQueryService.getBoothRanking();

        // then
        assertThat(result).isEmpty();
        verify(boothRepository, never()).findAllById(List.of());
    }

    @Test
    @DisplayName("랭킹 조회 시 좋아요 수 내림차순으로 부스 ID, 이름, 카운트를 반환한다")
    void getBoothRanking_정상반환() {
        // given
        Set<TypedTuple<String>> ranking = Set.of(
            new DefaultTypedTuple<>("1", 10.0),
            new DefaultTypedTuple<>("2", 5.0)
        );
        given(boothLikeService.getRanking()).willReturn(ranking);

        Booth booth1 = Booth.createBooth(1L, 1, "컴퓨터학부", BoothDivision.RELIGIOUS_DIVISION, null, null, null, "010-0000-0001");
        Booth booth2 = Booth.createBooth(1L, 2, "전자공학부", BoothDivision.ACADEMIC_DIVISION, null, null, null, "010-0000-0002");

        given(boothRepository.findAllById(anyIterable())).willReturn(List.of(booth1, booth2));

        // when
        List<BoothRankingResponseDto> result = boothQueryService.getBoothRanking();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(BoothRankingResponseDto::likeCount)
            .containsExactlyInAnyOrder(10L, 5L);
        assertThat(result).extracting(BoothRankingResponseDto::name)
            .containsExactlyInAnyOrder("컴퓨터학부", "전자공학부");
    }

    @Test
    @DisplayName("Redis에 있는 boothId가 DB에 없으면 이름은 빈 문자열로 반환된다")
    void getBoothRanking_DB에없는부스_빈이름반환() {
        // given
        Set<TypedTuple<String>> ranking = Set.of(
            new DefaultTypedTuple<>("999", 3.0)
        );
        given(boothLikeService.getRanking()).willReturn(ranking);
        given(boothRepository.findAllById(anyIterable())).willReturn(List.of());

        // when
        List<BoothRankingResponseDto> result = boothQueryService.getBoothRanking();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEmpty();
        assertThat(result.get(0).likeCount()).isEqualTo(3L);
    }
}

package kr.co.knuserver.domain.booth.repository;

import java.util.List;
import kr.co.knuserver.presentation.booth.dto.BoothRankingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoothLikeDailyRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<BoothRankingResponseDto> findTopByDate(String date, int limit) {
        return jdbcTemplate.query(
            "SELECT booth_id, name, like_count FROM booth_like_daily WHERE event_date = ? ORDER BY like_count DESC LIMIT ?",
            (rs, rowNum) -> new BoothRankingResponseDto(
                rs.getLong("booth_id"),
                rs.getString("name"),
                rs.getLong("like_count")
            ),
            date, limit
        );
    }
}

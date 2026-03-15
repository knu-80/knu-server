package kr.co.knuserver.domain.performance.repository;

import java.util.List;
import kr.co.knuserver.domain.performance.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    // 활성 부스의 공연만 startAt 오름차순으로 조회
    @Query("SELECT p FROM Performance p JOIN kr.co.knuserver.domain.booth.entity.Booth b ON p.boothId = b.id WHERE b.isActive = true ORDER BY p.performanceDuration.startAt ASC")
    List<Performance> findAllWithActiveBooth();
}

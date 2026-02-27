package kr.co.knuserver.domain.booth.repository;

import java.util.List;
import kr.co.knuserver.domain.booth.entity.Booth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoothRepository extends JpaRepository<Booth, Long> {

    List<Booth> findByIsActiveTrue();

    // 주어진 키워드가 부스명 or 부스 설명에 들어간 부스들을 조회
    @Query("SELECT b FROM Booth b WHERE b.isActive = true AND (b.name LIKE %:keyword% OR b.description LIKE %:keyword%)")
    List<Booth> searchByKeyword(@Param("keyword") String keyword);
}

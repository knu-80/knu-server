package kr.co.knuserver.domain.booth.repository;

import java.util.List;
import kr.co.knuserver.domain.booth.entity.Booth;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoothRepository extends JpaRepository<Booth, Long> {

    Integer countByIsActiveTrue();

    List<Booth> findByIsActiveTrueAndIdGreaterThanOrderByIdAsc(Long lastId, Pageable pageable);

    List<Booth> findByIsActiveTrue();

    // 주어진 키워드가 부스명 or 부스 설명에 들어간 부스들을 조회(페이지네이션 X)
    @Query("SELECT b FROM Booth b " +
        "WHERE b.isActive = true " +
        "AND (b.name LIKE CONCAT('%', :keyword, '%') " +
        "  OR CONCAT(',', b.keywords, ',') LIKE CONCAT('%,', :keyword, ',%')) " +
        "ORDER BY " +
        "  CASE " +
        "    WHEN b.name LIKE CONCAT('%', :keyword, '%') THEN 1 " + // 1순위: 이름에 키워드가 포함 (부분 일치)
        "    ELSE 2 " +                                             // 2순위: keywords에 해당 키워드가 포함 (정확히 일치)
        "  END ASC, " +
        "  b.boothNumber ASC")
    List<Booth> searchByKeyword(@Param("keyword") String keyword);


    // 페이지네이션 전용
    @Query("SELECT b FROM Booth b " +
        "WHERE b.isActive = true " +
        "AND b.id > :lastId " +
        "AND (b.name LIKE CONCAT('%', :keyword, '%') " +
        "  OR CONCAT(',', b.keywords, ',') LIKE CONCAT('%,', :keyword, ',%')) " +
        "ORDER BY b.id ASC")
    List<Booth> searchByKeywordWithCursor(@Param("keyword") String keyword, @Param("lastId") Long lastId, Pageable pageable);
}

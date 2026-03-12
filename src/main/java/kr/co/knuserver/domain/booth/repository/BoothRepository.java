package kr.co.knuserver.domain.booth.repository;

import java.util.List;
import kr.co.knuserver.domain.booth.entity.Booth;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoothRepository extends JpaRepository<Booth, Long> {

    List<Booth> findByIsActiveTrueAndIdGreaterThanOrderByIdAsc(Long lastId, Pageable pageable);

    @Query("SELECT b FROM Booth b " +
        "WHERE b.isActive = true " +
        "AND b.id > :lastId " +
        "AND (b.name LIKE CONCAT('%', :keyword, '%') " +
        "  OR CONCAT(',', b.keywords, ',') LIKE CONCAT('%,', :keyword, ',%')) " +
        "ORDER BY b.id ASC")
    List<Booth> searchByKeywordWithCursor(@Param("keyword") String keyword, @Param("lastId") Long lastId, Pageable pageable);
}

package kr.co.knuserver.domain.notice.repository;

import kr.co.knuserver.domain.notice.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE (:lastId IS NULL OR n.id < :lastId) ORDER BY n.id DESC")
    List<Notice> findNoticesByCursor(@Param("lastId") Long lastId, Pageable pageable);
}

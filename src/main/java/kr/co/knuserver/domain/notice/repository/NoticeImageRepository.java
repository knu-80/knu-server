package kr.co.knuserver.domain.notice.repository;

import kr.co.knuserver.domain.notice.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long> {

    List<NoticeImage> findAllByNoticeId(Long noticeId);

    List<NoticeImage> findAllByNoticeIdIn(List<Long> noticeIds);

    @Modifying(clearAutomatically = true)
    @Query("delete from NoticeImage ni where ni.noticeId = :noticeId")
    void deleteAllByNoticeIdInBatch(@Param("noticeId") Long noticeId);
}

package kr.co.knuserver.domain.notice.repository;

import kr.co.knuserver.domain.notice.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long> {

    List<NoticeImage> findAllByNoticeId(Long noticeId);

    void deleteAllByNoticeId(Long noticeId);
}

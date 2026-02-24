package kr.co.knuserver.domain.notice.repository;

import kr.co.knuserver.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}

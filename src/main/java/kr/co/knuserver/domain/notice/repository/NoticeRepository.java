package kr.co.knuserver.domain.notice.repository;

import kr.co.knuserver.domain.notice.entity.Notice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAll(Sort sort);
}

package kr.co.knuserver.domain.pubMenu.repository;

import kr.co.knuserver.domain.pubMenu.entity.PubMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PubMenuRepository extends JpaRepository<PubMenu, Long> {
    List<PubMenu> findByPubBoothId(Long pubBoothId);
}

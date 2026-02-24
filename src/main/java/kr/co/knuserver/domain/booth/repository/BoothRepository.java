package kr.co.knuserver.domain.booth.repository;

import kr.co.knuserver.domain.booth.entity.Booth;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoothRepository extends JpaRepository<Booth, Long> {
}

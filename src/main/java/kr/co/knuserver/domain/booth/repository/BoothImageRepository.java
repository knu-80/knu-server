package kr.co.knuserver.domain.booth.repository;

import kr.co.knuserver.domain.booth.entity.BoothImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoothImageRepository extends JpaRepository<BoothImage, Long> {

    List<BoothImage> findAllByBoothId(Long boothId);

    List<BoothImage> findAllByBoothIdIn(List<Long> boothIds);

    void deleteByBoothId(Long boothId);
}

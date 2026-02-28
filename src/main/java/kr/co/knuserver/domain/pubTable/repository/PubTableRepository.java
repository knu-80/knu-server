package kr.co.knuserver.domain.pubTable.repository;


import java.util.List;
import kr.co.knuserver.domain.pubTable.entity.PubTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubTableRepository  extends JpaRepository<PubTable, Long> {

    boolean existsByTableNumAndPubBoothId(int tableNum, Long pubBoothId);

    List<PubTable> findAllByPubBoothId(Long pubBoothId);
}

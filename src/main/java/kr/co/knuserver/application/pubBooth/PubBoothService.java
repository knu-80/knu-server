package kr.co.knuserver.application.pubBooth;

import kr.co.knuserver.domain.pubBooth.entity.PubBooth;
import kr.co.knuserver.domain.pubBooth.repository.PubBoothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PubBoothService {

    private final PubBoothRepository pubBoothRepository;

    public List<PubBooth> findAll() {
        return pubBoothRepository.findAll();
    }
}

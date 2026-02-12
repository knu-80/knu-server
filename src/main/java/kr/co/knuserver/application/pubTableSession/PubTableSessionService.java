package kr.co.knuserver.application.pubTableSession;

import kr.co.knuserver.domain.pubTableSession.entity.PubTableSession;
import kr.co.knuserver.domain.pubTableSession.repository.PubTableSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PubTableSessionService {

    private final PubTableSessionRepository pubTableSessionRepository;

    public PubTableSession getCurrentPubTableSession(Long pubTableId) {
        return pubTableSessionRepository.findByPubTableIdAndExitTimeIsNull(pubTableId);
    }
}

package kr.co.knuserver.application.booth;

import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoothReader {

    private final BoothRepository boothRepository;

    public Booth getBoothOrThrow(Long boothId) {
        return boothRepository.findById(boothId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.BOOTH_NOT_FOUND));
    }
}


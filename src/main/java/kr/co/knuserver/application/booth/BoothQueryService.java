package kr.co.knuserver.application.booth;

import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BoothQueryService {

    private final BoothReader boothReader;

    // 부스 조회
    public BoothInfoResponseDto getBooth(Long boothId) {
        Booth booth = boothReader.getBoothOrThrow(boothId);
        return BoothInfoResponseDto.fromEntity(booth);
    }

}

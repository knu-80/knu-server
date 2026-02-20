package kr.co.knuserver.application.booth;

import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRegisterRequestDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoothService {

    private final BoothRepository boothRepository;
    private final BoothReader boothReader;

    // 부스 생성
    public BoothInfoResponseDto registerBooth(
        BoothRegisterRequestDto request) {
        Booth result = boothRepository.save(BoothRegisterRequestDto.toEntity(request));

        return BoothInfoResponseDto.fromEntity(result);
    }

    // 부스 조회
    public BoothInfoResponseDto getBooth(Long boothId) {
        Booth booth = boothReader.getBoothOrThrow(boothId);
        return BoothInfoResponseDto.fromEntity(booth);
    }

    // 부스 수정
    public BoothInfoResponseDto updateBooth(Long boothId, BoothUpdateRequestDto request) {
        Booth booth = boothReader.getBoothOrThrow(boothId);
        booth.updateFromDto(request);

        return BoothInfoResponseDto.fromEntity(booth);
    }

    // 부스 삭제
    public void deleteBooth(Long boothId) {
        Booth booth = boothReader.getBoothOrThrow(boothId);
        boothRepository.delete(booth);
    }

}

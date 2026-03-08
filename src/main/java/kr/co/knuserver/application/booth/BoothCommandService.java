package kr.co.knuserver.application.booth;

import java.util.List;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothImage;
import kr.co.knuserver.domain.booth.repository.BoothImageRepository;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.infra.s3.S3Uploader;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRegisterRequestDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class BoothCommandService {

    private static final String S3_FOLDER = "booth";

    private final BoothRepository boothRepository;
    private final BoothImageRepository boothImageRepository;
    private final BoothReader boothReader;
    private final S3Uploader s3Uploader;

    // 부스 생성
    public BoothInfoResponseDto registerBooth(
        BoothRegisterRequestDto request, List<MultipartFile> images) {
        Booth result = boothRepository.save(BoothRegisterRequestDto.toEntity(request));

        List<String> imageUrls = uploadImages(result.getId(), images);

        return BoothInfoResponseDto.fromEntity(result, imageUrls);
    }

    // 부스 수정
    public BoothInfoResponseDto updateBooth(Long boothId, BoothUpdateRequestDto request) {
        Booth booth = boothReader.getBoothOrThrow(boothId);
        booth.updateFromDto(request);

        List<String> imageUrls = boothImageRepository.findAllByBoothId(boothId).stream()
            .map(BoothImage::getImageUrl)
            .toList();

        return BoothInfoResponseDto.fromEntity(booth, imageUrls);
    }

    // 부스 이미지 수정 (교체)
    public BoothInfoResponseDto updateBoothImages(Long boothId, List<MultipartFile> images) {
        Booth booth = boothReader.getBoothOrThrow(boothId);

        List<String> oldImageUrls = boothImageRepository.findAllByBoothId(boothId).stream()
            .map(BoothImage::getImageUrl)
            .toList();

        // 기존 S3 이미지 삭제
        oldImageUrls.forEach(s3Uploader::delete);
        boothImageRepository.deleteByBoothId(boothId);

        List<String> imageUrls = uploadImages(boothId, images);

        return BoothInfoResponseDto.fromEntity(booth, imageUrls);
    }

    // 부스 삭제
    public void deleteBooth(Long boothId) {
        Booth booth = boothReader.getBoothOrThrow(boothId);

        List<String> imageUrls = boothImageRepository.findAllByBoothId(boothId).stream()
            .map(BoothImage::getImageUrl)
            .toList();

        // S3 이미지 삭제 + DB 데이터 삭제
        imageUrls.forEach(s3Uploader::delete);
        boothImageRepository.deleteByBoothId(boothId);
        boothRepository.delete(booth);
    }

    private List<String> uploadImages(Long boothId, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }
        return images.stream()
            .filter(file -> !file.isEmpty())
            .map(file -> {
                String imageUrl = s3Uploader.upload(file, S3_FOLDER);
                boothImageRepository.save(BoothImage.of(boothId, imageUrl));
                return imageUrl;
            })
            .toList();
    }

}

package kr.co.knuserver.application.booth;

import java.util.List;
import kr.co.knuserver.domain.booth.entity.Booth;
import kr.co.knuserver.domain.booth.entity.BoothImage;
import kr.co.knuserver.domain.booth.repository.BoothImageRepository;
import kr.co.knuserver.domain.booth.repository.BoothRepository;
import kr.co.knuserver.domain.member.entity.Member;
import kr.co.knuserver.domain.member.entity.Role;
import kr.co.knuserver.domain.member.repository.MemberRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.infra.s3.S3Uploader;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRegisterRequestDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoothCommandService {

    private static final String S3_FOLDER = "booth";

    private final BoothRepository boothRepository;
    private final BoothImageRepository boothImageRepository;
    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;

    @Transactional
    public BoothInfoResponseDto registerBooth(
        BoothRegisterRequestDto request, List<MultipartFile> images) {
        try {
            Booth result = boothRepository.saveAndFlush(BoothRegisterRequestDto.toEntity(request));
            List<String> imageUrls = uploadImages(result.getId(), images);
            return BoothInfoResponseDto.fromEntity(result, imageUrls);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(BusinessErrorCode.ALREADY_EXISTS);
        }
    }

    @Transactional
    public BoothInfoResponseDto updateBooth(Long boothId, BoothUpdateRequestDto request, Long memberId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.BOOTH_NOT_FOUND));

        validateRole(booth, memberId);

        booth.updateFromDto(request);

        List<String> imageUrls = boothImageRepository.findAllByBoothId(boothId).stream()
            .map(BoothImage::getImageUrl)
            .toList();

        return BoothInfoResponseDto.fromEntity(booth, imageUrls);
    }

    @Transactional
    public BoothInfoResponseDto updateBoothImages(Long boothId, List<MultipartFile> images, Long memberId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.BOOTH_NOT_FOUND));

        validateRole(booth, memberId);

        List<String> oldImageUrls = boothImageRepository.findAllByBoothId(boothId).stream()
            .map(BoothImage::getImageUrl)
            .toList();

        oldImageUrls.forEach(s3Uploader::delete);
        boothImageRepository.deleteByBoothId(boothId);

        List<String> imageUrls = uploadImages(boothId, images);

        return BoothInfoResponseDto.fromEntity(booth, imageUrls);
    }

    @Transactional
    public void deleteBooth(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.BOOTH_NOT_FOUND));

        List<String> imageUrls = boothImageRepository.findAllByBoothId(boothId).stream()
            .map(BoothImage::getImageUrl)
            .toList();

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

    private void validateRole(Booth booth, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(BusinessErrorCode.MEMBER_NOT_FOUND));
        if (member.getRole() == Role.ADMIN) {
            return;
        }
        if (!booth.getMemberId().equals(memberId)) {
            throw new BusinessException(BusinessErrorCode.ACCESS_DENIED);
        }
    }

}

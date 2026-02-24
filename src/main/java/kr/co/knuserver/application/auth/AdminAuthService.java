package kr.co.knuserver.application.auth;

import kr.co.knuserver.domain.member.entity.Member;
import kr.co.knuserver.domain.member.repository.MemberRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.infra.jwt.JwtProvider;
import kr.co.knuserver.presentation.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminAuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Value("${admin.pin}")
    private String adminPin;

    public TokenResponse loginWithPin(String pin) {
        if (!adminPin.equals(pin)) {
            throw new BusinessException(BusinessErrorCode.INVALID_PIN);
        }

        Member admin = memberRepository.findAdmin()
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.ADMIN_NOT_FOUND));

        String accessToken = jwtProvider.createAccessToken(admin.getId());
        return TokenResponse.of(accessToken);
    }
}

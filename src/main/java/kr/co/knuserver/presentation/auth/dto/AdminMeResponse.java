package kr.co.knuserver.presentation.auth.dto;

import kr.co.knuserver.domain.member.entity.Member;

public record AdminMeResponse(
        Long memberId,
        String nickname,
        String loginId,
        String type,
        Long boothId
) {
    public static AdminMeResponse from(Member member) {
        return new AdminMeResponse(member.getId(), member.getNickname(), member.getLoginId(), member.getRole().name(), member.getBoothId());
    }
}

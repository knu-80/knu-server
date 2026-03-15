package kr.co.knuserver.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.knuserver.domain.member.entity.Member;
import kr.co.knuserver.domain.member.entity.Role;
import kr.co.knuserver.domain.member.repository.MemberRepository;
import kr.co.knuserver.global.exception.BusinessErrorCode;
import kr.co.knuserver.global.exception.BusinessException;
import kr.co.knuserver.infra.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RoleCheckInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RequireRole requireRole = resolveRequireRole(handlerMethod);
        if (requireRole == null) {
            return true;
        }

        Role requiredRole = requireRole.value();
        Member member = extractMember(request);

        if (member.getRole() != requiredRole) {
            throw new BusinessException(BusinessErrorCode.ACCESS_DENIED);
        }

        return true;
    }

    private RequireRole resolveRequireRole(HandlerMethod handlerMethod) {
        RequireRole methodAnnotation = handlerMethod.getMethodAnnotation(RequireRole.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }
        return handlerMethod.getBeanType().getAnnotation(RequireRole.class);
    }

    private Member extractMember(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            throw new BusinessException(BusinessErrorCode.UNAUTHORIZED_USER);
        }

        String token = header.substring(BEARER_PREFIX.length());
        Long memberId = jwtProvider.getMemberIdFromToken(token);

        return memberRepository.findById(memberId)
            .orElseThrow(() -> new BusinessException(BusinessErrorCode.UNAUTHORIZED_USER));
    }
}

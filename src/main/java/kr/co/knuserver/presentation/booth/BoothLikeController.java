package kr.co.knuserver.presentation.booth;

import kr.co.knuserver.application.booth.BoothLikeService;
import kr.co.knuserver.global.exception.ApiResponse;
import kr.co.knuserver.global.resolver.ClientIp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booths")
@RequiredArgsConstructor
public class BoothLikeController {

    private final BoothLikeService boothLikeService;

    @PostMapping("/{booth-id}/likes")
    public ResponseEntity<ApiResponse<Long>> like(
        @PathVariable(name = "booth-id") Long boothId,
        @CookieValue(name = "deviceId") String deviceId,
        @ClientIp String clientIp
    ) {
        long count = boothLikeService.like(boothId, deviceId, clientIp);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

}

package kr.co.knuserver.presentation.pubTableSession;

import jakarta.validation.Valid;
import kr.co.knuserver.application.pubTableSession.PubTableSessionService;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionEndRequestDto;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionStartRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/pubTableSession")
@RequiredArgsConstructor
public class PubTableSessionController {
    private final PubTableSessionService pubTableSessionService;

    @PostMapping("/start")
    public ResponseEntity<Void> startSession(@Valid @RequestBody PubTableSessionStartRequestDto request) {
        pubTableSessionService.startSession(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/end")
    public ResponseEntity<Void> endSession(@Valid @RequestBody PubTableSessionEndRequestDto request) {
        pubTableSessionService.endSession(request);
        return ResponseEntity.ok().build();
    }

    // TODO 테이블 주문 관련 API 추가 (#26)
}

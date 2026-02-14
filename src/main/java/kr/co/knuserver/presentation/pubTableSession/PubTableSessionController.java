package kr.co.knuserver.presentation.pubTableSession;

import jakarta.validation.Valid;
import kr.co.knuserver.application.pubTableSession.PubTableSessionService;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionEndRequest;
import kr.co.knuserver.presentation.pubTableSession.dto.PubTableSessionStartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pubTableSession")
@RequiredArgsConstructor
public class PubTableSessionController {
    private final PubTableSessionService pubTableSessionService;

    @PostMapping("/start")
    public ResponseEntity<Void> startSession(@Valid @RequestBody PubTableSessionStartRequest request) {
        pubTableSessionService.startSession(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/end")
    public ResponseEntity<Void> endSession(@Valid @RequestBody PubTableSessionEndRequest request) {
        pubTableSessionService.endSession(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO 테이블 주문 관련 API 추가 (#26)
}

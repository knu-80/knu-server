package kr.co.knuserver.presentation.pubWaiting;

import kr.co.knuserver.application.pubWaiting.PubWaitingCommandService;
import kr.co.knuserver.presentation.pubWaiting.dto.PubWaitingCancelRequestDto;
import kr.co.knuserver.presentation.pubWaiting.dto.PubWaitingRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pubWaiting")
@RequiredArgsConstructor
public class PubWaitingCommandController {
    private final PubWaitingCommandService pubWaitingCommandService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody PubWaitingRegisterRequestDto request) {
        pubWaitingCommandService.register(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<Void> cancel(@RequestBody PubWaitingCancelRequestDto request) {
        boolean cancelled = pubWaitingCommandService.cancel(request);
        if (!cancelled) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

}

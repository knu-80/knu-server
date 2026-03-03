package kr.co.knuserver.presentation.pubWaiting;

import kr.co.knuserver.application.pubWaiting.PubWaitingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pubWaiting")
@RequiredArgsConstructor
public class PubWaitingQueryController {

    private final PubWaitingQueryService pubWaitingQueryService;

    @GetMapping("/size/{pubBoothId}")
    public ResponseEntity<Integer> getWaitingSize(@PathVariable("pubBoothId") Long pubBoothId) {
        Integer waitingSize = pubWaitingQueryService.getWaitingSize(pubBoothId);
        return ResponseEntity.ok().body(waitingSize);
    }
}

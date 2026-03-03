package kr.co.knuserver.presentation.pubWaiting;

import java.util.List;
import kr.co.knuserver.application.pubWaiting.PubWaitingQueryService;
import kr.co.knuserver.presentation.pubWaiting.dto.PubWaitingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/pubWaiting")
@RequiredArgsConstructor
public class AdminPubWaitingCommandController {

    private final PubWaitingQueryService pubWaitingQueryService;

    @GetMapping("/list/{pubBoothId}")
    public ResponseEntity<List<PubWaitingResponseDto>> getWaitingList(@PathVariable("pubBoothId") Long pubBoothId) {
        List<PubWaitingResponseDto> waitingSize = pubWaitingQueryService.getWaitingList(pubBoothId);
        return ResponseEntity.ok().body(waitingSize);
    }
}

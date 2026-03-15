package kr.co.knuserver.presentation.pubOrder.controller;

import kr.co.knuserver.application.pubOrder.PubOrderQueryService;
import kr.co.knuserver.presentation.pubOrder.dto.PubOrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/pubOrder")
@RequiredArgsConstructor
public class PubOrderQueryController {

    private final PubOrderQueryService pubOrderQueryService;

    @GetMapping("/{pubTableSessionId}")
    public ResponseEntity<PubOrderResponseDto> getAllByPubTableSessionId(@PathVariable("pubTableSessionId") Long pubTableSessionId) {
        return  ResponseEntity.ok(pubOrderQueryService.getAllByPubTableSessionId(pubTableSessionId));
    }
}

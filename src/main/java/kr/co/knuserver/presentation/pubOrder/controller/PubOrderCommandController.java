package kr.co.knuserver.presentation.pubOrder.controller;

import jakarta.validation.Valid;
import kr.co.knuserver.application.pubOrder.PubOrderCommandService;
import kr.co.knuserver.presentation.pubOrder.dto.PubOrderRequestDto;
import kr.co.knuserver.presentation.pubOrder.dto.PubOrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/pubOrder")
@RequiredArgsConstructor
public class PubOrderCommandController {

    private PubOrderCommandService pubOrderCommandService;

    @PostMapping
    public ResponseEntity<PubOrderResponseDto> createOrder(@Valid @RequestBody PubOrderRequestDto request) {
        return ResponseEntity.ok().body(pubOrderCommandService.createOrder(request));
    }

    @DeleteMapping("/{pubOrderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("pubOrderId") Long pubOrderId) {
        pubOrderCommandService.deleteOrder(pubOrderId);
        return ResponseEntity.ok().build();
    }
}

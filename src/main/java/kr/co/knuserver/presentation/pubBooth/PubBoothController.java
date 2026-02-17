package kr.co.knuserver.presentation.pubBooth;

import kr.co.knuserver.presentation.pubBooth.dto.PubBoothDetailResponseDto;
import kr.co.knuserver.presentation.pubBooth.dto.PubBoothResponseDto;
import kr.co.knuserver.application.pubBooth.PubBoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pub-booths")
public class PubBoothController {

    private final PubBoothService pubBoothService;

    @GetMapping
    public ResponseEntity<List<PubBoothResponseDto>> findAll() {
        List<PubBoothResponseDto> pubBooths = pubBoothService.findAll().stream()
                .map(PubBoothResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pubBooths);
    }

    @GetMapping("/{pubBoothId}")
    public ResponseEntity<PubBoothDetailResponseDto> findById(@PathVariable Long pubBoothId) {
        PubBoothDetailResponseDto pubBoothDetailResponseDto = pubBoothService.findById(pubBoothId);
        return ResponseEntity.ok(pubBoothDetailResponseDto);
    }
}

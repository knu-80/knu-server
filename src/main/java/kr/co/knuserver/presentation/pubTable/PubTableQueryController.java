package kr.co.knuserver.presentation.pubTable;

import java.util.List;
import kr.co.knuserver.application.pubTable.PubTableQueryService;
import kr.co.knuserver.presentation.pubTable.dto.PubTableResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/pubTable")
@RequiredArgsConstructor
public class PubTableQueryController {

    private final PubTableQueryService pubTableQueryService;

    @GetMapping("/all/{pubBoothId}")
    public ResponseEntity<List<PubTableResponseDto>> getAllPubTables(@PathVariable Long pubBoothId) {
        List<PubTableResponseDto> pubTableResponseDtoList = pubTableQueryService.getAllPubTables(pubBoothId);
        return  ResponseEntity.ok(pubTableResponseDtoList);
    }
}

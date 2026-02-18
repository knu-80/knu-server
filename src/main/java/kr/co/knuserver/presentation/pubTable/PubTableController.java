package kr.co.knuserver.presentation.pubTable;

import jakarta.validation.Valid;
import java.util.List;
import kr.co.knuserver.application.pubTable.PubTableService;
import kr.co.knuserver.presentation.pubTable.dto.PubTableRequestDto;
import kr.co.knuserver.presentation.pubTable.dto.PubTableResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/pubTable")
@RequiredArgsConstructor
public class PubTableController {

    private final PubTableService pubTableService;

    @PostMapping
    public ResponseEntity<PubTableResponseDto> createPubTable(@Valid @RequestBody PubTableRequestDto pubTableRequestDto) {
        PubTableResponseDto pubTableResponseDto = pubTableService.createPubTable(pubTableRequestDto);
        return ResponseEntity.ok(pubTableResponseDto);
    }

    @GetMapping("/all/{pubBoothId}")
    public ResponseEntity<List<PubTableResponseDto>> getAllPubTables(@PathVariable Long pubBoothId) {
        List<PubTableResponseDto> pubTableResponseDtoList = pubTableService.getAllPubTables(pubBoothId);
        return  ResponseEntity.ok(pubTableResponseDtoList);
    }

    @PutMapping("/{pubTableId}")
    public ResponseEntity<PubTableResponseDto> updatePubTable(
            @Valid @RequestBody PubTableRequestDto pubTableRequestDto, @PathVariable Long pubTableId) {
        PubTableResponseDto pubTableResponseDto = pubTableService.updatePubTable(pubTableRequestDto, pubTableId);
        return ResponseEntity.ok(pubTableResponseDto);
    }

    @DeleteMapping("/{pubTableId}")
    public ResponseEntity<Void> deletePubTable(@PathVariable Long pubTableId) {
        pubTableService.deletePubTable(pubTableId);
        return ResponseEntity.ok().build();
    }
}

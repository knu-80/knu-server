package kr.co.knuserver.presentation.pubTable;

import jakarta.validation.Valid;
import java.util.List;
import kr.co.knuserver.application.pubTable.PubTableService;
import kr.co.knuserver.presentation.pubTable.dto.PubTableRequest;
import kr.co.knuserver.presentation.pubTable.dto.PubTableResponse;
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
@RequestMapping("/pubTable")
@RequiredArgsConstructor
public class PubTableController {

    private final PubTableService pubTableService;

    @PostMapping
    public ResponseEntity<PubTableResponse> createPubTable(@Valid @RequestBody PubTableRequest pubTableRequest) {
        PubTableResponse pubTableResponse = pubTableService.createPubTable(pubTableRequest);
        return ResponseEntity.ok(pubTableResponse);
    }

    @GetMapping("/all/{pubBoothId}")
    public ResponseEntity<List<PubTableResponse>> getAllPubTables(@PathVariable Long pubBoothId) {
        List<PubTableResponse> pubTableResponseList = pubTableService.getAllPubTables(pubBoothId);
        return  ResponseEntity.ok(pubTableResponseList);
    }

    @PutMapping("/{pubTableId}")
    public ResponseEntity<PubTableResponse> updatePubTable(
            @Valid @RequestBody PubTableRequest pubTableRequest, @PathVariable Long pubTableId) {
        PubTableResponse pubTableResponse = pubTableService.updatePubTable(pubTableRequest, pubTableId);
        return ResponseEntity.ok(pubTableResponse);
    }

    @DeleteMapping("/{pubTableId}")
    public ResponseEntity<Void> deletePubTable(@PathVariable Long pubTableId) {
        pubTableService.deletePubTable(pubTableId);
        return ResponseEntity.ok().build();
    }
}

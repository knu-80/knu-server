package kr.co.knuserver.presentation.pubMenu.admin;

import kr.co.knuserver.application.pubMenu.PubMenuCommandService;
import kr.co.knuserver.application.pubMenu.PubMenuQueryService;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuCreateRequestDto;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuResponseDto;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/pub-menus")
public class AdminPubMenuController implements AdminPubMenuControllerDocs {

    private final PubMenuCommandService pubMenuCommandService;
    private final PubMenuQueryService pubMenuQueryService;

    @Override
    @PostMapping
    public ResponseEntity<Long> createPubMenu(@RequestBody PubMenuCreateRequestDto requestDto) {
        Long pubMenuId = pubMenuCommandService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pubMenuId);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PubMenuResponseDto> getPubMenu(@PathVariable Long id) {
        PubMenuResponseDto pubMenuResponseDto = PubMenuResponseDto.from(pubMenuQueryService.findPubMenuById(id));
        return ResponseEntity.ok(pubMenuResponseDto);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePubMenu(@PathVariable Long id, @RequestBody PubMenuUpdateRequestDto requestDto) {
        pubMenuCommandService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePubMenu(@PathVariable Long id) {
        pubMenuCommandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
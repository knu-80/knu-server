package kr.co.knuserver.presentation.pubBooth.admin;

import kr.co.knuserver.application.pubBooth.PubBoothService;
import kr.co.knuserver.presentation.pubBooth.dto.PubBoothCreateRequestDto;
import kr.co.knuserver.presentation.pubBooth.dto.PubBoothUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/pub-booths")
public class AdminPubBoothController {

    private final PubBoothService pubBoothService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PubBoothCreateRequestDto requestDto) {
        Long id = pubBoothService.create(requestDto);
        return ResponseEntity.created(URI.create("/api/v1/pub-booths/" + id)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PubBoothUpdateRequestDto requestDto) {
        pubBoothService.update(id, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pubBoothService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

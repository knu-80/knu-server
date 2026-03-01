package kr.co.knuserver.presentation.pubMenu.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuCreateRequestDto;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuResponseDto;
import kr.co.knuserver.presentation.pubMenu.dto.PubMenuUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Admin PubMenu API", description = "관리자용 주점 메뉴 관련 API")
public interface AdminPubMenuControllerDocs {

    @Operation(summary = "주점 메뉴 생성", description = "새로운 주점 메뉴를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "주점 메뉴 생성 성공"),
    })
    ResponseEntity<Long> createPubMenu(@RequestBody PubMenuCreateRequestDto requestDto);

    @Operation(summary = "주점 메뉴 조회", description = "특정 주점 메뉴를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주점 메뉴 조회 성공"),
            @ApiResponse(responseCode = "404", description = "주점 메뉴를 찾을 수 없음"),
    })
    ResponseEntity<PubMenuResponseDto> getPubMenu(
            @Parameter(description = "주점 메뉴 ID", required = true) @PathVariable Long id);

    @Operation(summary = "주점 메뉴 수정", description = "특정 주점 메뉴를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주점 메뉴 수정 성공"),
            @ApiResponse(responseCode = "404", description = "주점 메뉴를 찾을 수 없음"),
    })
    ResponseEntity<Void> updatePubMenu(
            @Parameter(description = "주점 메뉴 ID", required = true) @PathVariable Long id,
            @RequestBody PubMenuUpdateRequestDto requestDto);

    @Operation(summary = "주점 메뉴 삭제", description = "특정 주점 메뉴를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "주점 메뉴 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "주점 메뉴를 찾을 수 없음"),
    })
    ResponseEntity<Void> deletePubMenu(
            @Parameter(description = "주점 메뉴 ID", required = true) @PathVariable Long id);
}

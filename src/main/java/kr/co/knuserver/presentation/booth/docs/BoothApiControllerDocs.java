package kr.co.knuserver.presentation.booth.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Booth API", description = "가두모집 부스 관련 API(일반 사용자/부스 운영자/총동연)")
public interface BoothApiControllerDocs {

    @Operation(summary = "가두모집 부스 단건 조회", description = "특정 가두모집 부스의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "부스 조회 성공"),
        @ApiResponse(responseCode = "404", description = "부스를 찾을 수 없음"),
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<BoothInfoResponseDto>> getBooth(
        @Parameter(description = "부스 ID", required = true) @PathVariable(name = "booth-id") Long boothId);

    @Operation(summary = "키워드 단위 가두모집 부스 리스트 조회", description = "주어진 키워드가 부스명 or 부스 설명에 들어간 모든 "
        + "부스를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "부스 조회 성공")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<List<BoothInfoResponseDto>>> searchBooths(
        @Parameter(description = "검색할 키워드 (입력하지 않으면 전체 조회)")
        @RequestParam(name = "keyword", required = false) String keyword
    );

    @Operation(summary = "가두모집 부스 정보 수정", description = "부스의 텍스트 필드(이름, 번호, 설명 등)를 수정합니다. 이미지 변경은 이미지 수정 API를 사용하세요.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "부스 수정 성공"),
        @ApiResponse(responseCode = "404", description = "부스를 찾을 수 없음"),
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<BoothInfoResponseDto>> updateBooth(
        @Parameter(description = "부스 ID", required = true) @PathVariable(name = "booth-id") Long boothId,
        @Valid @RequestBody BoothUpdateRequestDto request);

    @Operation(summary = "가두모집 부스 이미지 수정", description = "부스의 이미지를 교체합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "이미지 수정 성공"),
        @ApiResponse(responseCode = "404", description = "부스 없음")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<BoothInfoResponseDto>> updateBoothImages(
        @Parameter(description = "부스 ID", required = true) @PathVariable(name = "booth-id") Long boothId,
        @Parameter(description = "교체할 이미지 목록 (선택, 미전송 시 이미지 전체 삭제)")
        @RequestPart(value = "images", required = false) List<MultipartFile> images);
}

package kr.co.knuserver.presentation.booth.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRegisterRequestDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Admin Booth API", description = "개발진용 가두모집 부스 관련 API")
public interface AdminBoothApiControllerDocs {

    @Operation(summary = "가두모집 부스 생성", description = "새로운 가두모집 부스를 생성합니다. (multipart/form-data)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "부스 생성 성공"),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다.")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<BoothInfoResponseDto>> createBooth(
            @Parameter(description = "부스 생성 정보", required = true)
            @Valid @RequestPart("data") BoothRegisterRequestDto request,
            @Parameter(description = "부스 이미지 목록")
            @RequestPart(value = "images", required = false) List<MultipartFile> images);

    @Operation(summary = "가두모집 부스 정보 수정", description = "admin controller에도 이 API가 존재해야 할 것 같아서 추가합니다. 부스의 텍스트 필드(이름, 번호, 설명 등)를 수정합니다. 이미지 변경은 이미지 수정 API를 사용하세요.")
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

    @Operation(summary = "가두모집 부스 삭제", description = "특정 가두모집 부스를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "부스 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "부스를 찾을 수 없음"),
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<?>> deleteBooth(
            @Parameter(description = "부스 ID", required = true) @PathVariable(name = "booth-id") Long boothId);
}

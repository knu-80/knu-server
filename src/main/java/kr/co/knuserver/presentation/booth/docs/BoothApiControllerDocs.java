package kr.co.knuserver.presentation.booth.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import kr.co.knuserver.global.dto.CursorPaginationResponse;
import kr.co.knuserver.presentation.booth.dto.BoothCountResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothInfoResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothListResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothRankingResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothTop3ResponseDto;
import kr.co.knuserver.presentation.booth.dto.BoothUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Booth API", description = "가두모집 부스 관련 API(일반 사용자/부스 운영자/총동연)")
public interface BoothApiControllerDocs {

    @Operation(summary = "가두모집 부스 개수 조회", description = "현재 활성화된 가두모집 부스의 총 개수를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "부스 개수 조회 성공")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<BoothCountResponseDto>> getBoothCount();

    @Operation(summary = "가두모집 부스 단건 조회", description = "특정 가두모집 부스의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "부스 조회 성공"),
        @ApiResponse(responseCode = "404", description = "부스를 찾을 수 없음"),
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<BoothInfoResponseDto>> getBooth(
        @Parameter(description = "부스 ID", required = true) @PathVariable(name = "booth-id") Long boothId);

    @Operation(summary = "키워드 단위 가두모집 부스 리스트 조회(페이지네이션 X)", description = "주어진 키워드가 부스명 or 부스 설명에 들어간 모든 "
        + "부스를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "부스 조회 성공")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<List<BoothInfoResponseDto>>> searchBooths(
        @Parameter(description = "검색할 키워드 (입력하지 않으면 전체 조회)")
        @RequestParam(name = "keyword", required = false) String keyword
    );

    @Operation(summary = "키워드 단위 가두모집 부스 리스트 조회(페이지네이션 적용)", description = "주어진 키워드가 부스명 or 부스 설명에 들어간 "
        + "부스를 커서 기반 페이지네이션으로 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "부스 조회 성공")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<CursorPaginationResponse<BoothListResponseDto>>> searchBooths(
        @Parameter(description = "검색할 키워드 (입력하지 않으면 전체 조회)")
        @RequestParam(name = "keyword", required = false) String keyword,
        @Parameter(description = "마지막으로 조회된 부스 ID (첫 요청 시 null)")
        @RequestParam(name = "lastId", required = false) Long lastId,
        @Parameter(description = "페이지 크기 (기본값: 10)")
        @RequestParam(name = "size", required = false, defaultValue = "10") int size
    );

    @Operation(summary = "가두모집 부스 좋아요 랭킹 전체 조회", description = "좋아요 수 기준 전체 부스 랭킹을 반환합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "랭킹 조회 성공")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<List<BoothRankingResponseDto>>> getBoothRanking();

    @Operation(summary = "가두모집 부스 좋아요 TOP 3 조회", description = "좋아요 수 기준 상위 3개 부스를 반환합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "TOP 3 랭킹 조회 성공")
    })
    ResponseEntity<kr.co.knuserver.global.exception.ApiResponse<List<BoothTop3ResponseDto>>> getTop3BoothRanking();
}

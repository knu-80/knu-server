package kr.co.knuserver.global.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CursorPaginationResponse<T> {
    private final List<T> items;
    private final Long nextCursor;
    private final boolean hasNext;

    public static <T> CursorPaginationResponse<T> of(List<T> items, Long nextCursor, boolean hasNext) {
        return new CursorPaginationResponse<>(items, nextCursor, hasNext);
    }
}

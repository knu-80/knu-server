package kr.co.knuserver.application.notice;

import kr.co.knuserver.infra.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeImageEventListener {

    private final S3Uploader s3Uploader;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNoticeImageDelete(NoticeImageDeleteEvent event) {
        event.imageUrls().forEach(imageUrl -> {
            try {
                s3Uploader.delete(imageUrl);
            } catch (Exception e) {
                log.error("S3 이미지 삭제 실패. url: {}", imageUrl, e);
            }
        });
    }
}

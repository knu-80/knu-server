package kr.co.knuserver.domain.notice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LostFoundDetail {

    @Column(name = "found_place")
    private String foundPlace;

    @Column(name = "found_item")
    private String foundItem;
}

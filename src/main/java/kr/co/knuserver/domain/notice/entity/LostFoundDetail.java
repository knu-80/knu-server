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

    @Column(name = "keeping_place")
    private String keepingPlace;

    @Column(name = "lost_description")
    private String description;
}

package kr.co.knuserver.domain.pubMenu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.knuserver.domain.pubBooth.PubBooth;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pub_menu")
public class PubMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_menu_id")
    private Long id;

    private String name;
    private int price;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pub_booth_id")
    private PubBooth pubBooth;

    @Builder
    public PubMenu(String name, int price, String imageUrl, PubBooth pubBooth) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.pubBooth = pubBooth;
    }
}

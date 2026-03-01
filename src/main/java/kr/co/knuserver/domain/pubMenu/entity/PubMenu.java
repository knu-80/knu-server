package kr.co.knuserver.domain.pubMenu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.knuserver.domain.pubBooth.entity.PubBooth;
import kr.co.knuserver.global.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "pub_menu")
public class PubMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pub_menu_id")
    private Long id;

    private String name;
    private int price;
    private String imageUrl;

    @Column(name = "pub_booth_id")
    private Long pubBoothId;

    public static PubMenu createPubMenu(String name, int price, String imageUrl, Long pubBoothId) {
        return PubMenu.builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .pubBoothId(pubBoothId)
                .build();
    }

    public void update(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}

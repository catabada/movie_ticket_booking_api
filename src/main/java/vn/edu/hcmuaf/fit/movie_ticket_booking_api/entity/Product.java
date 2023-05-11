package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ProductType;

import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Product extends BaseObject {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name="stock")
    private Integer stock;

    @Column(name = "image")
    private String image;
}

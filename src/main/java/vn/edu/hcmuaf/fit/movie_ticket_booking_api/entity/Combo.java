package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "combo")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class Combo extends BaseObject {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @OneToMany(mappedBy = "combo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ComboItem> comboItems;



}

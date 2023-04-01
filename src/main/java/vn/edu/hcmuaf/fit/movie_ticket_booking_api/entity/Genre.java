package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;

import java.io.Serializable;

@Entity
@Table(name = "genre")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Genre extends BaseObject implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

}

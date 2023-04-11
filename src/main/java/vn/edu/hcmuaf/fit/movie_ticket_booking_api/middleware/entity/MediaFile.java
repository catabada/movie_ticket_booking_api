package vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class MediaFile {
    private String pathFolder;
    private String pathUrl;
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.MovieFormat;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.MovieState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

import java.util.Date;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class MovieDto extends BaseObjectDto {
    @NotNull
    protected String name;
    @NotNull
    protected String storyLine;
    protected String imageVertical;
    protected String imageHorizontal;

    protected List<GenreDto> genres;
    @NotNull
    protected Double rating;
    protected String slug;
    @NotNull
    protected Integer duration;
    private String language;
    private String subtitle;
    private String country;
    @NotNull
    protected String trailerUrl;
    @NotNull
    protected List<String> actors;
    @NotNull
    protected String director;
    @NotNull
    protected String producer;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    protected Date releaseDate;
    @NotNull
    protected MovieState movieState;
    @NotNull
    protected List<MovieFormat> movieFormats;

}

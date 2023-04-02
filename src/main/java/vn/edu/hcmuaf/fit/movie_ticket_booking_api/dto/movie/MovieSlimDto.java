package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.MovieFormat;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.MovieState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.genre.GenreDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class MovieSlimDto extends BaseObjectDto {
    @NotNull
    protected String name;
    @NotNull
    protected String storyLine;
    @NotNull
    protected String imageUrl;
    protected Set<GenreDto> genres;
    @NotNull
    protected Double rating;
    protected String slug;
    @NotNull
    protected Integer duration;
    @NotNull
    protected String trailerUrl;
    @NotNull
    protected List<String> actors;
    @NotNull
    protected String director;
    @NotNull
    protected String producer;
    @NotNull
    protected String releaseDate;
    @NotNull
    protected MovieState movieState;
    @NotNull
    protected Set<MovieFormat> movieFormats;
}

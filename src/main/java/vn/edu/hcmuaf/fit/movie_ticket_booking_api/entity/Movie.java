package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.MovieFormat;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.MovieState;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends BaseObject implements Serializable {
    @Column(name = "name")
    private String name;

    @Column(name = "story_line", columnDefinition = "TEXT")
    private String storyLine;

    @Column(name = "image_vertical")
    protected String imageVertical;
    @Column(name = "image_horizontal")
    protected String imageHorizontal;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "slug")
    private String slug;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "trailer_url")
    private String trailerUrl;
    @Column(name = "actors")
    @ElementCollection(targetClass = String.class)
    private List<String> actors;
    @Column(name = "director")
    private String director;
    @Column(name = "producer")
    private String producer;
    @Column(name = "language")
    private String language;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "country")
    private String country;
    @Column(name = "release_date")
    private Date releaseDate;
    @Enumerated(EnumType.STRING)
    private MovieState movieState;
    @Column(name = "movie_formats")
    @ElementCollection(targetClass = MovieFormat.class)
    @Enumerated(EnumType.STRING)
    private List<MovieFormat> movieFormats;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
}

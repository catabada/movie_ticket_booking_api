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

    @Column(name = "story_line")
    private String storyLine;

    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "slug")
    private String slug;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "trailer_url")
    private String trailerUrl;
    @Column(name = "actors")
    @ElementCollection(targetClass=String.class)
    private List<String> actors;
    @Column(name = "director")
    private String director;
    @Column(name = "producer")
    private String producer;
    @Column(name = "release_date")
    private String releaseDate;
    @Enumerated(EnumType.STRING)
    private MovieState movieState;
    @Column(name = "movie_formats")
    @ElementCollection(targetClass=MovieFormat.class)
    @Enumerated(EnumType.STRING)
    private List<MovieFormat> movieFormats;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
}

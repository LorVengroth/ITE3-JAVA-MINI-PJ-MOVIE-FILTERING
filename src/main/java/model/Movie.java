package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {


        @JsonProperty("id")
        private int id;

        @JsonProperty("title")
        private String title;

        @JsonProperty("original_title")
        private String originalTitle;

        @JsonProperty("release_date")
        private String releaseDate;

        @JsonProperty("vote_average")
        private double voteAverage;

        @JsonProperty("overview")
        private String overview;

        @JsonProperty("poster_path")
        private String posterPath;

        @JsonProperty("backdrop_path")
        private String backdropPath;

        @JsonProperty("popularity")
        private double popularity;

        @JsonProperty("vote_count")
        private int voteCount;

        @JsonProperty("adult")
        private boolean adult;

        @JsonProperty("video")
        private boolean video;

        @JsonProperty("genre_ids")
        private List<Integer> genreIds;

        @JsonProperty("original_language")
        private String originalLanguage;


        private String trailerUrl;
    }





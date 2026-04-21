package model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @JsonProperty("id")
    private String id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("name")
    private String name;

    @JsonProperty("site")
    private String site;

    @JsonProperty("type")
    private String type;

    @JsonProperty("size")
    private int size;

    @JsonProperty("official")
    private boolean official;

    @JsonProperty("published_at")
    private String publishedAt;

    @JsonProperty("iso_639_1")
    private String iso639_1;

    @JsonProperty("iso_3166_1")
    private String iso3166_1;
}
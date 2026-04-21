package model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideosResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("results")
    private List<Video> results;
}
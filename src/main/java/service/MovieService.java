package service;





import model.MovieDetail;
import model.SearchResponse;
import model.Video;

import java.util.List;

public interface MovieService {


    SearchResponse searchMovies(String query, int page);


    MovieDetail getMovieDetails(int movieId);

 
    List<Video> getMovieVideos(int movieId);

 
    String getTrailerUrl(int movieId);
}

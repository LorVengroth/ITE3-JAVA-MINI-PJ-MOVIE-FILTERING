package service;





import model.MovieDetail;
import model.SearchResponse;
import model.Video;

import java.util.List;

public interface MovieService {

    // Search movies by query with pagination
    SearchResponse searchMovies(String query, int page);

    // Get detailed information for a specific movie
    MovieDetail getMovieDetails(int movieId);

    // Get all videos (trailers, clips, etc.) for a movie
    List<Video> getMovieVideos(int movieId);

    // Get the official trailer URL for a movie
    String getTrailerUrl(int movieId);
}
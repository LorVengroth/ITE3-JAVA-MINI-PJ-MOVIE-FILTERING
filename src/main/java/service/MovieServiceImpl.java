package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieServiceImpl implements MovieService {

    private static final String API_KEY = "024853e37aa31c70a69b1ef53f299476";
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public MovieServiceImpl() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public SearchResponse searchMovies(String query, int page) {
        try {
            String url = String.format("%s/search/movie?api_key=%s&query=%s&page=%d",
                    BASE_URL, API_KEY, query.replace(" ", "+"), page);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                SearchResponse searchResponse = objectMapper.readValue(response.body(), SearchResponse.class);

                // For each movie, fetch its trailer URL
                for (Movie movie : searchResponse.getResults()) {
                    String trailerUrl = getTrailerUrl(movie.getId());
                    movie.setTrailerUrl(trailerUrl);
                }

                return searchResponse;
            } else {
                System.out.println("Error fetching movies: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Exception in searchMovies: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public MovieDetail getMovieDetails(int movieId) {
//        try {
//            String url = String.format("%s/movie/%d?api_key=%s&language=en-US",
//                    BASE_URL, movieId, API_KEY);
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .GET()
//                    .build();
//
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//            if (response.statusCode() == 200) {
//                return objectMapper.readValue(response.body(), MovieDetail.class);
//            } else {
//                System.out.println("Error fetching movie details: " + response.statusCode());
//                return null;
//            }
//        } catch (Exception e) {
//            System.out.println("Exception in getMovieDetails: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }


    @Override
    public MovieDetail getMovieDetails(int movieId) {
        try {
            String url = String.format("%s/movie/%d?api_key=%s&language=en-US",
                    BASE_URL, movieId, API_KEY);

            System.out.println("DEBUG: Calling URL - " + url);  // ADD THIS LINE

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("DEBUG: Response Code - " + response.statusCode());  // ADD THIS LINE
            System.out.println("DEBUG: Response Body - " + response.body());  // ADD THIS LINE

            if (response.statusCode() == 200) {
                MovieDetail detail = objectMapper.readValue(response.body(), MovieDetail.class);
                System.out.println("DEBUG: Movie found - " + detail.getTitle());  // ADD THIS LINE
                return detail;
            } else {
                System.out.println("Error fetching movie details: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Exception in getMovieDetails: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Video> getMovieVideos(int movieId) {
        try {
            String url = String.format("%s/movie/%d/videos?api_key=%s&language=en-US",
                    BASE_URL, movieId, API_KEY);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                VideosResponse videosResponse = objectMapper.readValue(response.body(), VideosResponse.class);
                return videosResponse.getResults();
            } else {
                System.out.println("Error fetching videos: " + response.statusCode());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Exception in getMovieVideos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public String getTrailerUrl(int movieId) {
        List<Video> videos = getMovieVideos(movieId);

        // First try to find an official trailer
        for (Video video : videos) {
            if ("Trailer".equals(video.getType()) && video.isOfficial() && "YouTube".equals(video.getSite())) {
                return "https://www.youtube.com/watch?v=" + video.getKey();
            }
        }

        // If no official trailer, find any trailer
        for (Video video : videos) {
            if ("Trailer".equals(video.getType()) && "YouTube".equals(video.getSite())) {
                return "https://www.youtube.com/watch?v=" + video.getKey();
            }
        }

        // If no trailer, find any video (clip, teaser, etc.)
        for (Video video : videos) {
            if ("YouTube".equals(video.getSite())) {
                return "https://www.youtube.com/watch?v=" + video.getKey();
            }
        }

        // No video found
        return "No trailer available";
    }
}
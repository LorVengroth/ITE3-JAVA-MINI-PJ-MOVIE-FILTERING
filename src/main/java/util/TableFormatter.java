package util;

import model.Movie;
import model.MovieDetail;
import java.util.List;

public class TableFormatter {

    public static void displaySearchResults(List<Movie> movies, int currentPage, int totalPages, int totalResults) {
        if (movies == null || movies.isEmpty()) {
            System.out.println("\nNo movies found.\n");
            return;
        }

        // Header
        System.out.println("\n" + "=".repeat(120));
        System.out.printf("%-8s | %-45s | %-12s | %-8s | %-40s\n",
                "ID", "Title", "Release", "Rating", "Trailer");
        System.out.println("-".repeat(120));

        // Rows
        for (Movie movie : movies) {
            String title = truncate(movie.getTitle(), 43);
            String release = movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty() ? movie.getReleaseDate() : "Unknown";
            String rating = String.format("%.1f", movie.getVoteAverage());
            String trailer = movie.getTrailerUrl() != null ? truncate(movie.getTrailerUrl(), 38) : "No trailer";

            System.out.printf("%-8d | %-45s | %-12s | %-8s | %-40s\n",
                    movie.getId(), title, release, rating, trailer);
        }

        System.out.println("=".repeat(120));
        System.out.printf("\nPage %d of %d | Total Results: %d\n", currentPage, totalPages, totalResults);
        System.out.println("\n[n] Next Page    [b] Back");
        System.out.println("[p] Previous Page   [e] Exit");
        System.out.println("[g] Go To");
        System.out.println("[md] Movie Detail");
        System.out.print("\nChoose an option: ");
    }

    public static void displayMovieDetail(MovieDetail detail) {
        if (detail == null) {
            System.out.println("\nMovie not found.\n");
            return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("| Movie Information");
        System.out.println("=".repeat(50));

        System.out.printf("| %-12s | %-30s\n", "Title", detail.getTitle());
        System.out.printf("| %-12s | %-30s\n", "Release", detail.getReleaseDate());
        System.out.printf("| %-12s | %-30s\n", "Rating", String.format("%.1f / 10", detail.getVoteAverage()));
        System.out.printf("| %-12s | %-30s\n", "Runtime", detail.getRuntime() + " min");
        System.out.printf("| %-12s | %-30s\n", "Budget", formatCurrency(detail.getBudget()));

        // Genres
        String genres = "";
        if (detail.getGenres() != null) {
            for (int i = 0; i < detail.getGenres().size(); i++) {
                if (i > 0) genres += ", ";
                genres += detail.getGenres().get(i).getName();
            }
        }
        System.out.printf("| %-12s | %-30s\n", "Genres", genres);

        // Origin
        String origin = "";
        if (detail.getOriginCountry() != null) {
            origin = String.join(", ", detail.getOriginCountry());
        }
        System.out.printf("| %-12s | %-30s\n", "Origin", origin);

        System.out.println("=".repeat(50));
        System.out.println("\nMOVIE INFORMATION\n");
    }

    private static String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }

    private static String formatCurrency(long amount) {
        if (amount == 0) return "$0";
        return String.format("$%,d", amount);
    }
}
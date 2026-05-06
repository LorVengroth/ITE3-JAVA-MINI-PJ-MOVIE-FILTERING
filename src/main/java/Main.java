import model.MovieDetail;
import model.SearchResponse;
import service.MovieService;
import service.MovieServiceImpl;
import util.TableFormatter;

import java.util.Scanner;

public class Main {
    private static final MovieService movieService = new MovieServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);

    private static String currentQuery = "";
    private static int currentPage = 1;
    private static int totalPages = 0;
    private static int totalResults = 0;
    private static SearchResponse currentSearchResponse = null;
    static void main() {


            System.out.println("=== TMDB Movie App ===");

            while (true) {
               
                if (currentQuery.isEmpty()) {
                    System.out.print("\n[-] Enter movie title: ");
                    currentQuery = scanner.nextLine().trim();

                    if (currentQuery.isEmpty()) {
                        System.out.println("Please enter a movie title.");
                        continue;
                    }

                    currentPage = 1;
                    currentSearchResponse = movieService.searchMovies(currentQuery, currentPage);

                    if (currentSearchResponse == null || currentSearchResponse.getResults().isEmpty()) {
                        System.out.println("No movies found for: " + currentQuery);
                        currentQuery = "";
                        continue;
                    }

                    totalPages = currentSearchResponse.getTotalPages();
                    totalResults = currentSearchResponse.getTotalResults();

                    // Display results
                    TableFormatter.displaySearchResults(
                            currentSearchResponse.getResults(),
                            currentPage,
                            totalPages,
                            totalResults
                    );
                }

             
                String option = scanner.nextLine().trim().toLowerCase();

                switch (option) {
                    case "n": 
                        if (currentPage < totalPages) {
                            currentPage++;
                            currentSearchResponse = movieService.searchMovies(currentQuery, currentPage);
                            if (currentSearchResponse != null) {
                                TableFormatter.displaySearchResults(
                                        currentSearchResponse.getResults(),
                                        currentPage,
                                        totalPages,
                                        totalResults
                                );
                            }
                        } else {
                            System.out.println("You're already on the last page!");
                            TableFormatter.displaySearchResults(
                                    currentSearchResponse.getResults(),
                                    currentPage,
                                    totalPages,
                                    totalResults
                            );
                        }
                        break;

                    case "p": 
                        if (currentPage > 1) {
                            currentPage--;
                            currentSearchResponse = movieService.searchMovies(currentQuery, currentPage);
                            if (currentSearchResponse != null) {
                                TableFormatter.displaySearchResults(
                                        currentSearchResponse.getResults(),
                                        currentPage,
                                        totalPages,
                                        totalResults
                                );
                            }
                        } else {
                            System.out.println("You're already on the first page!");
                            TableFormatter.displaySearchResults(
                                    currentSearchResponse.getResults(),
                                    currentPage,
                                    totalPages,
                                    totalResults
                            );
                        }
                        break;

                    case "b": 
                        currentQuery = "";
                        currentPage = 1;
                        totalPages = 0;
                        totalResults = 0;
                        currentSearchResponse = null;
                        System.out.println("\nGoing back to search...\n");
                        break;

                    case "g": 
                        System.out.print("[-] Enter page number (1-" + totalPages + "): ");
                        try {
                            int pageNum = Integer.parseInt(scanner.nextLine().trim());
                            if (pageNum >= 1 && pageNum <= totalPages) {
                                currentPage = pageNum;
                                currentSearchResponse = movieService.searchMovies(currentQuery, currentPage);
                                if (currentSearchResponse != null) {
                                    TableFormatter.displaySearchResults(
                                            currentSearchResponse.getResults(),
                                            currentPage,
                                            totalPages,
                                            totalResults
                                    );
                                }
                            } else {
                                System.out.println("Invalid page number! Please enter between 1 and " + totalPages);
                                TableFormatter.displaySearchResults(
                                        currentSearchResponse.getResults(),
                                        currentPage,
                                        totalPages,
                                        totalResults
                                );
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                            TableFormatter.displaySearchResults(
                                    currentSearchResponse.getResults(),
                                    currentPage,
                                    totalPages,
                                    totalResults
                            );
                        }
                        break;

                    case "md": 
                        System.out.print("[-] Enter movie id: ");
                        try {
                            int movieId = Integer.parseInt(scanner.nextLine().trim());
                            MovieDetail detail = movieService.getMovieDetails(movieId);
                            TableFormatter.displayMovieDetail(detail);

                        
                            System.out.println("\nPress Enter to continue...");
                            scanner.nextLine();
                            TableFormatter.displaySearchResults(
                                    currentSearchResponse.getResults(),
                                    currentPage,
                                    totalPages,
                                    totalResults
                            );
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid movie ID.");
                            TableFormatter.displaySearchResults(
                                    currentSearchResponse.getResults(),
                                    currentPage,
                                    totalPages,
                                    totalResults
                            );
                        }
                        break;

                    case "e": 
                        System.out.println("Goodbye!");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid option! Please choose: n, p, b, g, md, or e");
                        TableFormatter.displaySearchResults(
                                currentSearchResponse.getResults(),
                                currentPage,
                                totalPages,
                                totalResults
                        );
                        break;
                }
            }
        }
    }

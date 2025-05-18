import java.io.*;
import java.util.*;

public class MoviesDatabase {

    private static final String FILE_NAME = "movies.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Movie Database Menu =====");
            System.out.println("1. Add Movie");
            System.out.println("2. Search Movie");
            System.out.println("3. Display All Movies");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addMovie(scanner);
                case 2 -> searchMovie(scanner);
                case 3 -> displayAllMovies();
                case 4 -> System.out.println("Exiting program...");
                default -> System.out.println("Invalid choice. Please choose again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void addMovie(Scanner scanner) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            System.out.print("Enter movie title: ");
            String title = scanner.nextLine();
            System.out.print("Enter director: ");
            String director = scanner.nextLine();
            System.out.print("Enter year: ");
            String year = scanner.nextLine();
            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();

            String movieEntry = "\n"+title + "," + director + "," + year + "," + genre;
            bw.write(movieEntry);
            bw.newLine();

            System.out.println("Movie added successfully!");

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void searchMovie(Scanner scanner) {
        System.out.print("Enter movie title to search: ");
        String searchTitle = scanner.nextLine().toLowerCase();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] movie = line.split(",");
                if (movie.length >= 1 && movie[0].toLowerCase().contains(searchTitle)) {
                    System.out.println("\nTitle   : " + movie[0]);
                    System.out.println("Director: " + movie[1]);
                    System.out.println("Year    : " + movie[2]);
                    System.out.println("Genre   : " + movie[3]);
                    found = true;
                    System.out.println("----------------------------------");
                }
            }

            if (!found) {
                System.out.println("Movie not found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    private static void displayAllMovies() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n===== All Movies =====");
            while ((line = br.readLine()) != null) {
                String[] movie = line.split(",");
                if (movie.length >= 4) {
                    System.out.println("\nTitle   : " + movie[0]);
                    System.out.println("Director: " + movie[1]);
                    System.out.println("Year    : " + movie[2]);
                    System.out.println("Genre   : " + movie[3]);
                    System.out.println("------------------------------");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No movies found. File does not exist.");
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }
}

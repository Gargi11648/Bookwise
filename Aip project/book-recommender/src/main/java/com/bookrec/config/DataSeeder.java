package com.bookrec.config;

import com.bookrec.model.*;
import com.bookrec.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final BookRepository books; private final UserRepository users; private final RatingRepository ratings;
    public DataSeeder(BookRepository b, UserRepository u, RatingRepository r) { books = b; users = u; ratings = r; }

    @Override
    public void run(String... args) {
        if (books.count() > 0) return;
        List<Book> b = books.saveAll(List.of(
            new Book("Dune", "Frank Herbert", "Sci-Fi", 1965, "Epic desert-planet saga of politics and prophecy."),
            new Book("Neuromancer", "William Gibson", "Sci-Fi", 1984, "The cyberpunk classic."),
            new Book("The Martian", "Andy Weir", "Sci-Fi", 2011, "An astronaut stranded on Mars."),
            new Book("Foundation", "Isaac Asimov", "Sci-Fi", 1951, "The fall and rebirth of a galactic empire."),
            new Book("Pride and Prejudice", "Jane Austen", "Romance", 1813, "Manners, marriage and misjudgement."),
            new Book("Jane Eyre", "Charlotte Bronte", "Romance", 1847, "An orphan's journey to independence."),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", 1937, "There and back again."),
            new Book("A Game of Thrones", "George R.R. Martin", "Fantasy", 1996, "Noble houses at war."),
            new Book("Gone Girl", "Gillian Flynn", "Thriller", 2012, "A marriage gone terribly wrong."),
            new Book("The Girl with the Dragon Tattoo", "Stieg Larsson", "Thriller", 2005, "Journalist and hacker uncover a cold case."),
            new Book("Sapiens", "Yuval Noah Harari", "Non-Fiction", 2011, "A brief history of humankind."),
            new Book("Atomic Habits", "James Clear", "Non-Fiction", 2018, "Tiny changes, remarkable results.")));
        List<AppUser> u = users.saveAll(List.of(new AppUser("alice"), new AppUser("bob"), new AppUser("carol")));
        int[][] s = {{0,0,5},{0,1,4},{0,6,3},{1,0,5},{1,2,5},{1,3,4},{1,8,2},{2,4,5},{2,5,4},{2,6,4},{2,7,5}};
        for (int[] x : s) ratings.save(new Rating(u.get(x[0]), b.get(x[1]), x[2]));
    }
}

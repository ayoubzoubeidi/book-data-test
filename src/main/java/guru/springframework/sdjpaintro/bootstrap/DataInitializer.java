package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.Author;
import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by jt on 6/12/21.
 */

@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (bookRepository.count() == 0L) {

            Author evans = new Author("Eric", "Evans", new Date(), new HashSet<>());
            Author walls = new Author("Craig", "Walls", new Date(), new HashSet<>());

            Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", new HashSet<>());

            bookDDD.getAuthors().add(evans);
            evans.getBooks().add(bookDDD);

            System.out.println("Id: " + bookDDD.getId());

            Book savedDDD = bookRepository.save(bookDDD);

            System.out.println("Id: " + savedDDD.getId());

            Book bookSIA = new Book("Spring In Action", "234234", "Oriely", new HashSet<>());

            bookSIA.getAuthors().add(walls);
            walls.getBooks().add(bookSIA);

            Book savedSIA = bookRepository.save(bookSIA);

            bookRepository.findAll().forEach(book -> {
                System.out.println("Book Id: " + book.getId());
                System.out.println("Book Title: " + book.getTitle());
            });
        }

    }
}

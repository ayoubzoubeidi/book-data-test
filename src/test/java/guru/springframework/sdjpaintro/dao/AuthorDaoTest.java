package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap", "guru.springframework.sdjpaintro.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    void getById() {
        Author author = authorDao.getById(1L);
        assertNotNull(author);
    }

    @Test
    void findAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig");

        assertEquals("Craig", author.getFirstName());
        assertEquals("Walls", author.getLastName());
    }

    @Test
    void saveNewAuthor() {
        Author author = new Author("Alex", "Ferguson", new Date());
        Author savedAuthor = authorDao.saveNewAuthor(author);
        assertNotNull(savedAuthor);
        assertNotNull(savedAuthor.getId());
    }

    @Test
    void updateAuthor() {
        Author author = new Author("Alex", "Ferguson", new Date());
        Author savedAuthor = authorDao.saveNewAuthor(author);

        savedAuthor.setFirstName("Sir");
        savedAuthor.setLastName("Alex");

        authorDao.updateAuthor(savedAuthor);

        Author updatedAuthor = authorDao.getById(savedAuthor.getId());

        assertEquals("Sir", updatedAuthor.getFirstName());
        assertEquals("Alex", updatedAuthor.getLastName());

    }

    @Test
    void deleteAuthorById() {
        Author author = new Author("Alex", "Ferguson", new Date());
        Author savedAuthor = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(savedAuthor.getId());

        Author deletedAuthor = authorDao.getById(savedAuthor.getId());

        assertNull(deletedAuthor);
        assertNotNull(savedAuthor.getId());

    }
}

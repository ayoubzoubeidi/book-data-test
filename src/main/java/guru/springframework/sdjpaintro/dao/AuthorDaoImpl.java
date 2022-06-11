package guru.springframework.sdjpaintro.dao;

import guru.springframework.sdjpaintro.domain.Author;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Author getById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Author.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Author findAuthorByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a " +
                    "WHERE a.firstName = :first_name", Author.class);
            query.setParameter("first_name", name);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(author);
            em.flush();
            em.clear();
            em.getTransaction().commit();
            return author;
        } finally {
            em.close();
        }
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            Author updatedAuthor = em.merge(author);
            em.flush();
            em.clear();
            em.getTransaction().commit();
            return updatedAuthor;

        } finally {
            em.close();
        }
    }

    @Override
    public void deleteAuthorById(Long id) {

        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            Author author = em.getReference(Author.class, id);
            em.remove(author);
            em.flush();
            em.clear();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }
}

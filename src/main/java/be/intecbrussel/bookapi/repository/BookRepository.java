package be.intecbrussel.bookapi.repository;

import be.intecbrussel.bookapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenresContainingIgnoreCase(String title, String author, String genre);

    List<Book> findBooksByPopularBookIsTrue();

    boolean existsBookByTitleEqualsIgnoreCase(String title);

}

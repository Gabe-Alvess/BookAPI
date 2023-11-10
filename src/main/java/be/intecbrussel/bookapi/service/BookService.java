package be.intecbrussel.bookapi.service;

import be.intecbrussel.bookapi.model.Book;
import be.intecbrussel.bookapi.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> findBookById(long id) {
        return bookRepository.findById(id);
    }

    public Optional<List<Book>> findAllBooks() {
        List<Book> dbBooks = bookRepository.findAll();

        if (dbBooks.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(dbBooks);
    }

    public Optional<List<Book>> findPopularBooks() {
        List<Book> popularBooks = bookRepository.findBooksByPopularBookIsTrue();

        if (popularBooks.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(popularBooks);
    }

    public Optional<List<Book>> searchBooks(String userSearch) {
        List<Book> searchResult =
                bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenresContainingIgnoreCase(userSearch, userSearch, userSearch);

        if (searchResult.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(searchResult);
    }

    public Optional<Book> addBook(Book book) {
        boolean exists = bookRepository.existsBookByTitleEqualsIgnoreCase(book.getTitle());

        if (exists) {
            return Optional.empty();
        }

        Book newBook = new Book(book.getImgURL(), book.getTitle(), book.getAuthor(), book.getGenres(), book.getDescription(), book.getReleaseDate());
        bookRepository.save(newBook);

        newBook.setPopularBook(newBook.getId() % 2 == 0);
        bookRepository.flush();

        return Optional.of(newBook);
    }

    public boolean addMultipleBooks(List<Book> books) {
        boolean alreadyExists = false;

        for (Book book : books) {
           alreadyExists = bookRepository.existsBookByTitleEqualsIgnoreCase(book.getTitle());
        }

        if (alreadyExists) {
            return false;
        }

        bookRepository.saveAll(books);

        return true;
    }

    public void updateBook(Book book) {
        bookRepository.saveAndFlush(book);
    }

    public Optional<Book> update(Long bookId, Book book) {
        Optional<Book> optionalBook = findBookById(bookId);

        if (optionalBook.isEmpty()) {
            return Optional.empty();
        }

        Book dbBook = optionalBook.get();

        dbBook.setImgURL(!book.getImgURL().trim().isEmpty() ? book.getImgURL() : dbBook.getImgURL());
        dbBook.setTitle(!book.getTitle().trim().isEmpty() ? book.getTitle() : dbBook.getTitle());
        dbBook.setAuthor(!book.getAuthor().trim().isEmpty() ? book.getAuthor() : dbBook.getAuthor());
        dbBook.setDescription(!book.getDescription().isEmpty() ? book.getDescription() : dbBook.getDescription());
        dbBook.setGenres(!book.getGenres().trim().isEmpty() ? book.getGenres() : dbBook.getGenres());
        dbBook.setReleaseDate(!book.getReleaseDate().trim().isEmpty() ? book.getReleaseDate() : dbBook.getReleaseDate());

        updateBook(dbBook);

        return Optional.of(dbBook);
    }

    public Optional<Book> deleteBookById(Long id) {
        Optional<Book> optionalBook = findBookById(id);

        if (optionalBook.isEmpty()) {
            return Optional.empty();
        }

        bookRepository.deleteById(id);

        return optionalBook;
    }
}

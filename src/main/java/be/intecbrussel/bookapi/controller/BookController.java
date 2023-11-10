package be.intecbrussel.bookapi.controller;

import be.intecbrussel.bookapi.model.Book;
import be.intecbrussel.bookapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/get")
    public ResponseEntity getBook(@RequestParam Long id) {
        Optional<Book> optionalBook = bookService.findBookById(id);

        if (optionalBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Book book = optionalBook.get();

        return ResponseEntity.ok(book);
    }

    @GetMapping("/search")
    public ResponseEntity searchForBooks(@RequestParam String userSearch) {
        Optional<List<Book>> optionalSearchResult = bookService.searchBooks(userSearch);

        if (optionalSearchResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Book> searchResult = optionalSearchResult.get();

        return ResponseEntity.ok(searchResult);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllBooks() {
        Optional<List<Book>> allBooks = bookService.findAllBooks();

        if (allBooks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Book> books = allBooks.get();

        return ResponseEntity.ok(books);
    }

    @GetMapping("/popular")
    public ResponseEntity getPopularBooks() {
        Optional<List<Book>> optionalPopularBooks = bookService.findPopularBooks();

        if (optionalPopularBooks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Book> popularBooks = optionalPopularBooks.get();

        return ResponseEntity.ok(popularBooks);
    }

    @PostMapping("/adm/add")
    public ResponseEntity addBook(@RequestBody Book book) {
        Optional<Book> optionalBook = bookService.addBook(book);

        if (optionalBook.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Book addedBook = optionalBook.get();

        return ResponseEntity.ok(addedBook);
    }

    @PostMapping("/adm/multiAdd")
    public ResponseEntity addMultipleBooks(@RequestBody List<Book> books) {
        boolean success = bookService.addMultipleBooks(books);

        if (!success) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/adm/update")
    public ResponseEntity updateBook(@RequestParam Long id, @RequestBody Book book) {
        Optional<Book> optionalBook = bookService.update(id, book);

        if (optionalBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/adm/delete")
    public ResponseEntity deleteBook(@RequestParam Long id) {
        Optional<Book> optionalBook = bookService.deleteBookById(id);

        if (optionalBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();
    }
}

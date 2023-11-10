package be.intecbrussel.bookapi.service;

import be.intecbrussel.bookapi.model.Book;
import be.intecbrussel.bookapi.model.BorrowedBook;
import be.intecbrussel.bookapi.model.AuthUser;
import be.intecbrussel.bookapi.model.dto.BBResponse;
import be.intecbrussel.bookapi.repository.BorrowedBookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBookService {
    private final BorrowedBookRepository borrowedBookRepository;
    private final BookService bookService;

    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository, BookService bookService) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookService = bookService;
    }

    public Optional<BorrowedBook> getBorrowedBook(long borrowedBookId) {
        return borrowedBookRepository.findById(borrowedBookId);
    }

    public Optional<List<BBResponse>> findBorrowedBooks(String email) {
        List<BorrowedBook> borrowedBooksByUser = borrowedBookRepository.findBorrowedBookByUser_Email(email);

        if (borrowedBooksByUser.isEmpty()) {
            return Optional.empty();
        }

        List<BBResponse> borrowedBooks = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (BorrowedBook borrowedBook : borrowedBooksByUser) {
            Book book = borrowedBook.getBook();
            String borrowDate = borrowedBook.getBorrowDate().format(formatter);
            String dueDate = borrowedBook.getDueDate().format(formatter);
            boolean renewed = borrowedBook.isRenewed();

            borrowedBooks.add(new BBResponse(borrowedBook.getId(), book.getImgURL(), book.getTitle(), borrowDate, dueDate, renewed));
        }

        return Optional.of(borrowedBooks);
    }

    public Optional<BorrowedBook> borrowBook(AuthUser user, Book book) {

        BorrowedBook borrowedBook = new BorrowedBook(user, book, LocalDate.now(), LocalDate.now().plusMonths(1));
        borrowedBookRepository.save(borrowedBook);
        book.setAvailable(false);
        bookService.updateBook(book);

        return borrowedBookRepository.findBorrowedBookByBook_Id(book.getId());
    }

    public void returnBorrowedBook(Long borrowedBookId) {
            borrowedBookRepository.deleteById(borrowedBookId);
    }

    public boolean renewBorrowedBook(AuthUser user, long borrowedBookId) throws Exception {
        boolean bookFound = false;

        for (BorrowedBook borrowedBook : user.getBorrowedBooks()) {
            if (borrowedBook.getId() == borrowedBookId) {
                if (LocalDate.now().isBefore(borrowedBook.getDueDate())) {
                    borrowedBook.setDueDate(borrowedBook.getDueDate().plusWeeks(2));
                    borrowedBook.setRenewed(true);

                    bookFound = true;
                    break;
                } else {
                    throw new Exception("Can't be renewed, due date expired!");
                }
            }
        }

        if (bookFound) {
            return  true;
        } else {
            throw new Exception("Borrowed Book Not Found!");
        }
    }
}

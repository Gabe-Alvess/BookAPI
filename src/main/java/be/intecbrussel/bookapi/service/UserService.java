package be.intecbrussel.bookapi.service;

import be.intecbrussel.bookapi.model.AuthUser;
import be.intecbrussel.bookapi.model.Book;
import be.intecbrussel.bookapi.model.BorrowedBook;
import be.intecbrussel.bookapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BookService bookService;
    private final BorrowedBookService borrowedBookService;

    public UserService(UserRepository userRepository, BookService bookService, BorrowedBookService borrowedBookService) {
        this.userRepository = userRepository;
        this.bookService = bookService;
        this.borrowedBookService = borrowedBookService;
    }

    public Optional<AuthUser> findUser(String email) {
        return userRepository.findById(email);
    }

    public void updateUser(AuthUser user) {
        userRepository.saveAndFlush(user);
    }

    public void addProfilePhoto(MultipartFile file, String email) throws Exception {
        Optional<AuthUser> optionalUser = findUser(email);

        if (optionalUser.isEmpty()) {
            throw new Exception("User Not Found!");
        }

        AuthUser user = optionalUser.get();

        user.setProfilePhoto(file.getBytes());
        userRepository.flush();
    }

//    public byte[] getProfilePhoto(String email) throws Exception {
//        Optional<AuthUser> optionalUser = findUser(email);
//
//        if (optionalUser.isEmpty()) {
//            throw new Exception("User Not Found!");
//        }
//
//        AuthUser user = optionalUser.get();
//
//        byte[] profilePhoto = user.getProfilePhoto();
//
//        if (profilePhoto.length == 0) {
//            throw new Exception("No Profile Photo");
//        }
//
//        return profilePhoto;
//    }

    public Optional<BorrowedBook> borrowBook(String userEmail, Long bookId) {
        Optional<Book> optionalBook = bookService.findBookById(bookId);
        Optional<AuthUser> optionalUser = findUser(userEmail);
        Optional<BorrowedBook> borrowedBook;

        if (optionalBook.isPresent() && optionalUser.isPresent()) {
            borrowedBook = borrowedBookService.borrowBook(optionalUser.get(), optionalBook.get());

            borrowedBook.ifPresent(book -> optionalUser.get().getBorrowedBooks().add(book));

            updateUser(optionalUser.get());

            return borrowedBook;
        }

        return Optional.empty();
    }

    public boolean returnBorrowedBook(String userEmail, long borrowedBookId) throws Exception {
        Optional<AuthUser> user = findUser(userEmail);

        if (user.isEmpty()) {
            throw new Exception("User not found!");
        }

        AuthUser currentUser = user.get();

        Iterator<BorrowedBook> iterator = currentUser.getBorrowedBooks().iterator();

        while (iterator.hasNext()) {
            BorrowedBook borrowedBook = iterator.next();

            if (borrowedBook.getId() == borrowedBookId && LocalDate.now().isBefore(borrowedBook.getDueDate())) {
                Optional<BorrowedBook> optionalBorrowedBook = borrowedBookService.getBorrowedBook(borrowedBookId);

                if (optionalBorrowedBook.isPresent()) {
                    BorrowedBook returnedBook = optionalBorrowedBook.get();

                    Book book = returnedBook.getBook();

                    iterator.remove();
                    updateUser(currentUser);

                    borrowedBookService.returnBorrowedBook(borrowedBookId);

                    book.setAvailable(true);
                    bookService.updateBook(book);

                    return true;
                }
            }
        }

        return false;
    }


    public void renewBorrowedBook(String userEmail, long borrowedBookId) throws Exception {
        Optional<AuthUser> optionalUser = findUser(userEmail);

        if (optionalUser.isEmpty()) {
            throw new Exception("User Not Found!");
        }

        boolean response = borrowedBookService.renewBorrowedBook(optionalUser.get(), borrowedBookId);

        if (response) {
            updateUser(optionalUser.get());
        }
    }
}

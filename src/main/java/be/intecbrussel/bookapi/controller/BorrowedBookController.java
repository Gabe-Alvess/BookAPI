package be.intecbrussel.bookapi.controller;

import be.intecbrussel.bookapi.model.BorrowedBook;
import be.intecbrussel.bookapi.model.dto.BBResponse;
import be.intecbrussel.bookapi.service.BorrowedBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/borrowed")
public class BorrowedBookController {
    private final BorrowedBookService borrowedBookService;

    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        this.borrowedBookService = borrowedBookService;
    }

    @GetMapping("/getAll")
    public ResponseEntity getBorrowedBooks(@RequestParam String email) {
        Optional<List<BBResponse>> optionalBB = borrowedBookService.findBorrowedBooks(email);

        if (optionalBB.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<BBResponse> borrowedBooks = optionalBB.get();

        return ResponseEntity.ok(borrowedBooks);
    }
}

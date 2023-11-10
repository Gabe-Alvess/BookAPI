package be.intecbrussel.bookapi.controller;

import be.intecbrussel.bookapi.model.AuthUser;
import be.intecbrussel.bookapi.model.BorrowedBook;
import be.intecbrussel.bookapi.model.dto.UserResponse;
import be.intecbrussel.bookapi.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity getUser(@RequestParam String email) {
        Optional<AuthUser> optionalUser = userService.findUser(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AuthUser user = optionalUser.get();

        UserResponse response = new UserResponse(user.getFirstName(), user.getLastName(), user.getProfilePhoto());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/addPhoto")
    public ResponseEntity addPhoto(@RequestParam String email, @RequestBody MultipartFile file) {
        try {
            userService.addProfilePhoto(file, email);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/borrow")
    public ResponseEntity borrowBook(@RequestParam String email, @RequestParam Long bookId) {
        Optional<BorrowedBook> borrowedBook = userService.borrowBook(email, bookId);

        if (borrowedBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/return")
    public ResponseEntity returnBook(@RequestParam String email, @RequestParam Long bbId) {
        try {
            boolean success = userService.returnBorrowedBook(email, bbId);

            if (!success) {
                return ResponseEntity.unprocessableEntity().build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/renew")
    public ResponseEntity renewDueDate(@RequestParam String email, @RequestParam Long bbId) {
        try {
            userService.renewBorrowedBook(email, bbId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}

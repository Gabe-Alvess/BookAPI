package be.intecbrussel.bookapi.repository;

import be.intecbrussel.bookapi.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findBorrowedBookByUser_Email(String email);
    Optional<BorrowedBook> findBorrowedBookByBook_Id(Long id);
}

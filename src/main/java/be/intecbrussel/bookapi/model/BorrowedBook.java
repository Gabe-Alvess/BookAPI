package be.intecbrussel.bookapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "borrowed_book_tb")
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    private AuthUser user;
    @OneToOne
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean renewed = false;

    public BorrowedBook(AuthUser user, Book book, LocalDate borrowDate, LocalDate dueDate) {
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    protected BorrowedBook() {
    }

    public long getId() {
        return id;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isRenewed() {
        return renewed;
    }

    public void setRenewed(boolean renewed) {
        this.renewed = renewed;
    }

    @Override
    public String toString() {
        return "BorrowedBook{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", renewed=" + renewed +
                '}';
    }
}

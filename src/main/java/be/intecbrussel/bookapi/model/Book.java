package be.intecbrussel.bookapi.model;

import jakarta.persistence.*;

import java.util.Random;

@Entity(name = "book_tb")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String imgURL;
    private String title;
    private String author;
    private String genres;
    @Column(length = 1000)
    private String description;
    private String releaseDate;
    private boolean available = true;
    private boolean popularBook;
    public Book(String imgURL, String title, String author, String genres, String description, String releaseDate) {
        this.imgURL = imgURL;
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    protected Book() {
    }

    public long getId() {
        return id;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String img) {
        this.imgURL = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPopularBook(boolean popularBook) {
        this.popularBook = popularBook;
    }

    public boolean isPopularBook() {
        return popularBook;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", imgURL='" + imgURL + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genres='" + genres + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", available=" + available +
                ", popularBook=" + popularBook +
                '}';
    }
}

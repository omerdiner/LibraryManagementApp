package Entities;

public class Book {
    private int id;
    private String name;
    private String author;
    private int page;
    private String publisher;
    private String genre;

    public Book(int id, String name, String author, int page, String publisher, String genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.page = page;
        this.publisher = publisher;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

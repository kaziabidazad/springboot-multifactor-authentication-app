package com.duke.mfa.poc.dto;

import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;

/**
 * @author Kazi
 */
public class BookDto implements CommonResponseDto {

    private Integer bookId;
    private String bookName;
    private String author;
    private List<String> coAuthors;
    private String isbn;
    private String publisher;
    private String publishedDate;
    private Integer pages;
    private String coverUrl;

    public BookDto() {
    }

    /**
     * @param bookId
     * @param bookName
     * @param author
     * @param coAuthors
     * @param isbn
     * @param publisher
     * @param publishedDate
     * @param pages
     * @param coverUrl
     */
    public BookDto(Integer bookId, String bookName, String author, List<String> coAuthors, String isbn,
            String publisher, String publishedDate, Integer pages, String coverUrl) {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.coAuthors = coAuthors;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pages = pages;
        this.coverUrl = coverUrl;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(List<String> coAuthors) {
        this.coAuthors = coAuthors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public String toString() {
        return "BookDto [bookId=" + bookId + ", bookName=" + bookName + ", author=" + author + ", coAuthors="
                + coAuthors + ", isbn=" + isbn + ", publisher=" + publisher + ", publishedDate=" + publishedDate
                + ", pages=" + pages + ", coverUrl=" + coverUrl + "]";
    }

    public static List<BookDto> generateBooks(int numberOfBooks) {
        List<BookDto> books = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < numberOfBooks; i++) {
            Book book = faker.book();
            BookDto bookDto = new BookDto(i, book.title(), book.author(), null, faker.phoneNumber().phoneNumber(),
                    book.publisher(), faker.date().toString(), faker.number().randomDigitNotZero(),
                    faker.internet().image());
            books.add(bookDto);
        }
        return books;

    }

}

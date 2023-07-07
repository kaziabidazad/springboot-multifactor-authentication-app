package com.duke.mfa.poc.dto;

import java.util.List;

/**
 * @author Kazi
 */
public class BookResponseDto extends ResponseDto {

    private List<BookDto> books;

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BookResponseDto [books=" + books + "]";
    }

}

package com.duke.mfa.poc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duke.mfa.poc.dto.BookDto;
import com.duke.mfa.poc.dto.BookResponseDto;
import com.duke.mfa.poc.utils.Endpoints;

/**
 * @author Kazi
 */
@RestController
@RequestMapping(path = Endpoints.API + Endpoints.BOOKS)
public class BooksController implements Endpoints {

    @GetMapping(path = GET_BOOKS)
    public BookResponseDto getBooks() {
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setBooks(BookDto.generateBooks(20));
        bookResponseDto.setMessage("Success");
        return bookResponseDto;
    }
}

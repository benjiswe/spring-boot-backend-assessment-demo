package com.example.demo.dao;


import com.example.demo.model.Book;
import com.example.demo.model.ReturnResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// define operations allowed for anyone implementing this interface
public interface BookDao {

    // insert a new book with given id
    ReturnResponse insertBook (UUID id, Book book);


    // insert book without an id, randomly generated
    default ReturnResponse insertBook(Book book){
        UUID id = UUID.randomUUID();
        book.setBookID(id);
        return insertBook(id, book);
    }

    List<Book> selectAllBook();

//    int deleteBookById(UUID id);

    ReturnResponse updateBookById(UUID id,Book book);

    Optional<Book> selectBookById(UUID id);

    boolean checkBookId(UUID id);

    List<Book> selectAllBooksByPagination(int pageNumber);

    ReturnResponse getBook3rdPartyApiCall (UUID id);

}

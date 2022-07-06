package com.example.demo.service;

import com.example.demo.dao.BookDao;
import com.example.demo.model.Book;
import com.example.demo.model.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


// why service separate from dao?
// https://stackoverflow.com/questions/10025028/what-is-dao-and-service-layer-exactly-in-spring-framework#:~:text=Services%20should%20call%20DAOs%20and,to%20span%20several%20DAO%20calls.
// tldr-> dao = handle connection to data storage
// service = contain logic
// decoupling benefits,

// talked to by api layer to get data - service layer
// @Component / @Service (more specific, better readability to show it is a service class )  -> to show that this is a bean
@Service
public class BookService {


    //reference to DAO + add into constructor
    private final BookDao bookDao;


    // dependency injection
    @Autowired
    public BookService (@Qualifier("mssql") BookDao bookDao){
        this.bookDao = bookDao;
    }
    @Transactional // rollback if one of many statements failed executed in transaction
    public ReturnResponse addBook (Book book){
        return bookDao.insertBook(book);
    }

    public List<Book> getAllBooks(){
        return bookDao.selectAllBook();
    }

    public Optional<Book> getBookById(UUID id){
        return bookDao.selectBookById(id);
    }

    @Transactional
    public ReturnResponse updateBook (UUID id, Book newBook){
        return bookDao.updateBookById(id,newBook);
    }

    public boolean checkBookID (UUID id){return bookDao.checkBookId(id);}

    public List<Book> getAllBooksByPage(int pageNumber){
        return bookDao.selectAllBooksByPagination(pageNumber);
    }

    public ReturnResponse getBooksFrom3rdPartyApiCall (UUID id){
        System.out.println("called api");
        return bookDao.getBook3rdPartyApiCall(id);
    }

}

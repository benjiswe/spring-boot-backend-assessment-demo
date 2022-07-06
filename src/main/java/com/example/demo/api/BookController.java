package com.example.demo.api;

import com.example.demo.model.Book;
import com.example.demo.model.ReturnResponse;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RequestMapping("demobookapi")

@RestController
public class BookController {

    private final BookService bookService;

    // dont have instance of bookService , will work if --> this.bookService = new bookService(); --> but not work properly or not ideal, so use dependency injection
    @Autowired // BookService should be autowired, instantiated and injected into this constructor, but BookService must also be spring bean
    public BookController (BookService bookService){
        this.bookService = bookService;
    }

    // POST API to add a new book record into the database

    @PostMapping
    @Transactional
    public ReturnResponse addBook(@Valid @NonNull @RequestBody Book book){
        ReturnResponse rr = bookService.addBook(book);
        return rr;
    }

    // GET API to get all books from the database

    @GetMapping
    public ReturnResponse getAllBooks(){
        try{
            List<Book> bS = bookService.getAllBooks();
            ReturnResponse rr = new ReturnResponse(true,"Successfully Retrieved", new Object[]{bS.toArray()}, HttpStatus.OK.value());
            rr.setReturnRecords(bS.size());
            return rr;
        }catch(Exception e){
            e.printStackTrace();
            ReturnResponse rr = new ReturnResponse(false,"An Error Has Occurred",e.toString(),HttpStatus.BAD_REQUEST.value());
            rr.setReturnRecords(0);
            return rr;
        }
    }

    // GET API to get the books by page number (pagination)

    @RequestMapping(method=GET, value={"/page","/page/{num}"})
    public ReturnResponse getBookPagination(@PathVariable(name="num",required= false) Integer num){
        if(num == null){ // if paging number not supplied, set to 0 default
            num = 0;
        }
        try{
           List<Book> bS = bookService.getAllBooksByPage(num);
           ReturnResponse rr = new ReturnResponse(true,"Successfully Retrieved - Page "+String.valueOf(num), new Object[]{bS.toArray()}, HttpStatus.OK.value());
           rr.setReturnRecords(bS.size());
           return rr;
        }catch(Exception e){
            e.printStackTrace();
            ReturnResponse rr= new ReturnResponse(false,"An Error Has Occurred",e.toString(),HttpStatus.BAD_REQUEST.value());
            rr.setReturnRecords(0);
            return rr;
        }
    }

    // GET Book By ID Api to get book details by the id supplied.

    // get the id from the path and return to the parameter of the method
    @GetMapping(path="/{id}")
    public ReturnResponse getBookById(@PathVariable("id") UUID id){
        System.out.println("GET request received!(for indiv. book id)");
        ReturnResponse rr;
        try{
            Book book = bookService.getBookById(id).orElse(null);
            String apiMsg = "";
            apiMsg = book==null?"No Book Match Found":"Successfully Retrieved";// if there is no book with matching id found
            rr = new ReturnResponse(true,apiMsg,book,HttpStatus.OK.value());
            rr.setReturnRecords(book==null?0:1);
            return rr;
        }catch(Exception e){
            e.printStackTrace();
            return new ReturnResponse(false,"An Error Has Occurred",e.toString(),HttpStatus.BAD_REQUEST.value());
        }
    }

    // PUT API to update specific book in the database
    @PutMapping(path="{id}")
    public ReturnResponse updateBook(@PathVariable("id") UUID id,@RequestBody Book bookToUpdate){
        boolean checkIDExist = bookService.checkBookID(id);
        if (bookService.checkBookID(id) == false){
            return new ReturnResponse(true,"Invalid Book ID Supplied","-",HttpStatus.OK.value());
        }
        ReturnResponse rr = bookService.updateBook(id,bookToUpdate);
        return rr;
    }


    // GET API to search for the book availability in OpenLibrary API by inserting book's name into query call to 3rd Party API (OpenLibrary)

    @GetMapping(path="/search/{id}")
    public ReturnResponse searchBookStringFrom3rdPartyApi(@PathVariable("id") UUID id){

        boolean checkIDExist = bookService.checkBookID(id);
        if (bookService.checkBookID(id) == false){
            return new ReturnResponse(true,"Invalid Book ID Supplied","-",HttpStatus.OK.value());
        }
        ReturnResponse rr = bookService.getBooksFrom3rdPartyApiCall(id);
        return rr;
    }
}

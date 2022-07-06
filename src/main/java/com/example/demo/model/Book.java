package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Book {
    // unique id
    private UUID bookID;
    //    private final UUID bookID;
    // make sure it is not blank
    @NotBlank(message="Book Name must be supplied and not be blank")
    private String bookName;
    //   private final String bookName;
    @NotBlank(message="Book Description must be supplied and not be blank")
    private String bookDescription;

    public Book (@JsonProperty("id") UUID bookID, @JsonProperty("name") String bookName, @JsonProperty("description") String bookDescription){
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookDescription = bookDescription;
    }
    public Book (){};

    public void setBookID(UUID bookID) {
        this.bookID = bookID;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public UUID getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                ", bookDescription='" + bookDescription + '\'' +
                '}';
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
}

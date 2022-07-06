package com.example.demo.bookmapper;

import com.example.demo.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


// custom mapper to take into account for the string -> UUID
public class bookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

        UUID newID = UUID.fromString(rs.getString("id"));
        String bookName = rs.getString("name");
        String bookDescription = rs.getString("description");
        Book newBook = new Book(newID,bookName,bookDescription);
//        System.out.println("Generated new Book Object from MSSQL! It is= "+newBook.toString());
        return newBook;
    }
}

package com.example.demo.dao;

import com.example.demo.bookmapper.bookMapper;
import com.example.demo.bookmapper.countMapper;
import com.example.demo.model.Book;
import com.example.demo.model.BookQuery;
import com.example.demo.model.ReturnResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Repository("mssql")
public class BookDataAccessService implements BookDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public ReturnResponse insertBook(UUID id, Book book) {
        System.out.println("inserting book");
        ReturnResponse rr;
        Book newBookToInsert = new Book(id,book.getBookName(),book.getBookDescription());
        try{
            String sqlQuery = "INSERT "+" INTO "+" Book "+" (id,name,description) "+" VALUES "+" (?,?,?)";
            int result = jdbcTemplate.update(sqlQuery, new Object[]{newBookToInsert.getBookID(),newBookToInsert.getBookName(),newBookToInsert.getBookDescription()});
        }catch(Exception e){
            e.printStackTrace();
            rr = new ReturnResponse(false,"Failed to Add new Book into the system","-",HttpStatus.BAD_REQUEST.value());
            rr.setReturnRecords(0);
            return rr;
        }
        rr = new ReturnResponse(true,"Successfully Added New Book Into the System",newBookToInsert,HttpStatus.OK.value());
        rr.setReturnRecords(1);
        return rr;
    }

    @Override
    public List<Book> selectAllBook() {
        String sqlQuery = "Select "+" * "+ " from "+" Book ";
//        List<Book> books = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class)); // old method
        List<Book> books = jdbcTemplate.query(sqlQuery, new bookMapper());
        return books;
    }

    @Override
    public ReturnResponse updateBookById(UUID id, Book book) {
        String sqlQuery = "UPDATE "+" Book "+" SET ";
        int res;
        ReturnResponse rr;
        try{
            if(book.getBookName() != null && book.getBookDescription() != null){
                sqlQuery+= " name = ? "+" , "+" description = ? "+" WHERE "+" id "+" = "+"'"+id.toString()+"'";
                res = jdbcTemplate.update(sqlQuery,book.getBookName(),book.getBookDescription());
            }else if(book.getBookName() != null && book.getBookDescription() == null){
                sqlQuery+= " name = ? "+" WHERE "+" id "+" = "+"'"+id.toString()+"'";
                res = jdbcTemplate.update(sqlQuery,book.getBookName());
            }else if (book.getBookDescription()!= null && book.getBookName() == null){
                sqlQuery += " description = ? "+" WHERE "+" id "+" = "+"'"+id.toString()+"'";
                res = jdbcTemplate.update(sqlQuery,book.getBookDescription());
            }else{
                rr = new ReturnResponse(false,"No Fields to Update - Please Supply Necessary Fields to Update Book Details","-",HttpStatus.BAD_REQUEST.value());
                rr.setReturnRecords(0);
                return rr;
            }
            Optional<Book> updatedBook = selectBookById(id);
            if(res == 1){ // if successfully update that 1 book record
                rr = new ReturnResponse(true,"Successfully Updated Fields for Book",updatedBook,HttpStatus.OK.value());
                rr.setReturnRecords(1);
                return rr;
            }else{ // if no record were updated (something went wrong)
                rr = new ReturnResponse(false,"Something Happened When Updating Fields",updatedBook,HttpStatus.BAD_REQUEST.value());
                rr.setReturnRecords(0);
                return rr;
            }
        }catch(Exception e){
            e.printStackTrace();
            rr = new ReturnResponse(false,"An Error Has Occurred",e.toString(),HttpStatus.BAD_REQUEST.value());
            rr.setReturnRecords(0);
            return rr;
        }
    }


    @Override
    public Optional<Book> selectBookById(UUID id) {
        String sqlQuery = "SELECT "+ " * "+ " FROM "+" Book "+ " WHERE "+" id = "+"'"+id.toString()+"'";
        return jdbcTemplate.query(sqlQuery,new bookMapper()).stream().findFirst(); // return first match (id is unique and PK)
    }

    @Override
    public boolean checkBookId(UUID id) {
        String sqlQuery = "SELECT "+ " COUNT(*) "+ " FROM "+" Book "+ " WHERE "+" id = "+"'"+id.toString()+"'";        // return either result or not result
        if(jdbcTemplate.query(sqlQuery,new countMapper()).size() == 0){ //if list empty, no matching book id found in record
            System.out.println("No Record");
            return false;
        } // else return true as there will be 1 record (id is unique, will always be one unique id);
        System.out.println("Record Found");
        return true;
    }

    @Override
    public List<Book> selectAllBooksByPagination(int pageNumber) {
        int recordsPerPage = 10; // defined requirements 10 records per page
        int offsetNumber;
        String sqlQuery = "Select "+" * "+" from "+" Book "+" ORDER BY "+" name "+" ASC "+
                " OFFSET ";
        if(pageNumber == 0){ // see first 10 rows only, offset 0;
            offsetNumber = pageNumber;
        }else{ // multiply by rows per page to skip earlier rows.
            offsetNumber = pageNumber*recordsPerPage;
        }
        sqlQuery += String.valueOf(offsetNumber)+" ROWS "+" FETCH NEXT "+ String.valueOf(recordsPerPage)+ " ROWS "+ " ONLY ";
        System.out.println(sqlQuery);
        List<Book> books = jdbcTemplate.query(sqlQuery, new bookMapper());
        return books;
    }

    @Override
    public ReturnResponse getBook3rdPartyApiCall(UUID id) {
        Optional<Book> book = selectBookById(id);
        String bookName = book.get().getBookName();
        String formattedBookName = String.join("+",bookName.split(" ",0)); // format to appropriate query (space replaced with +)

        final String url = "http://"+"openlibrary.org"+"/search"+"."+"json?"+"q="+formattedBookName; // GET API URL
        Map<String,Object> map;
        ReturnResponse rr;
        int recordsReturned;
        try{
            RestTemplate rt = new RestTemplate();
            ResponseEntity<BookQuery> re = rt.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<BookQuery>() {
            });
            BookQuery bq = re.getBody();
            recordsReturned = bq.getDocs().size();
            System.out.println(url);
            System.out.println(bq.toString());
            map = new HashMap<>();
            map.put("Number of Records Found In OpenLibrary",bq.getNum_found());
            map.put("Book Title Queried",bq.getQ());
            map.put("Results (First "+recordsReturned+" records returned)",bq.getDocs());
        }catch(Exception e){
            e.printStackTrace();
            rr =  new ReturnResponse(true,
                    "Error Encountered Trying from OpenLibrary Repo via API",
                        e.toString(),HttpStatus.BAD_REQUEST.value());
            return rr;
        }
       rr = new ReturnResponse(true,"Sucessfully Retrieved from OpenLibrary Repo via API",
                map,HttpStatus.OK.value());
        rr.setReturnRecords(recordsReturned);
        return rr;
    }
}

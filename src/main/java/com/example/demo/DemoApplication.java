package com.example.demo;

import com.example.demo.model.Person;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	// declare instance jdbc template
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// kickstart application
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	// setup table & log file
	@Override
	public void run(String... args) throws Exception {

		// setup table
		String initialSQLSetup = "CREATE TABLE Book"+"(id varchar(255) NOT NULL PRIMARY KEY,"+"name varchar(255) NOT NULL,"+"description varchar(255) NOT NULL)";
		try{
			jdbcTemplate.execute(initialSQLSetup);
			System.out.println("Table Created - Setup Success");
		}catch(org.springframework.jdbc.UncategorizedSQLException ef){
			System.out.println("Table Exists or SQL Exception Encountered");
			System.out.println(ef.getSQLException().toString());
		}catch(Exception e){
			System.out.println("Something went wrong!");
			System.out.println(e.toString());
		}

		// setup log
		FileService fs;
		Boolean sf;
		try{
			fs = new FileService();
			sf = fs.fileSetup();
		}catch(Exception e){
			System.out.println("Something Went Wrong - Setup Log File");
			System.out.println(e.toString());
		}
	}
}

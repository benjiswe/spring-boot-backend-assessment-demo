package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class BookQueryDocs {
    private String title;
    private List<String> author_name;
    private int first_publish_year;
    private int edition_count;

    public BookQueryDocs(String title, List<String> author_name, int first_publish_year, int edition_count) {
        this.title = title;
        this.author_name = author_name;
        this.first_publish_year = first_publish_year;
        this.edition_count = edition_count;
    }
    public BookQueryDocs(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(List<String> author_name) {
        this.author_name = author_name;
    }

    public int getFirst_publish_year() {
        return first_publish_year;
    }

    public void setFirst_publish_year(int first_publish_year) {
        this.first_publish_year = first_publish_year;
    }

    public int getEdition_count() {
        return edition_count;
    }

    public void setEdition_count(int edition_count) {
        this.edition_count = edition_count;
    }


    @Override
    public String toString() {
        return "BookQueryDocs{" +
                "title='" + title + '\'' +
                ", author_name=" + author_name +
                ", first_publish_year=" + first_publish_year +
                ", edition_count=" + edition_count +
                '}';
    }
}

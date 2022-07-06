package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class BookQuery {

    private int start;
    private int num_found;
    private List<BookQueryDocs> docs;
    private String q;

    public BookQuery(int start, int num_found, List<BookQueryDocs> docs, String q) {
        this.start = start;
        this.num_found = num_found;
        this.docs = docs;

        this.q = q;
    }
    public BookQuery(){};

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNum_found() {
        return num_found;
    }

    public void setNum_found(int num_found) {
        this.num_found = num_found;
    }

    public List<BookQueryDocs> getDocs() {
        return docs;
    }

    public void setDocs(List<BookQueryDocs> docs) {
        this.docs = docs;
    }


    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    @Override
    public String toString() {
        return "BookQuery{" +
                "start=" + start +
                ", num_found=" + num_found +
                ", docs=" + docs +
                ", q='" + q + '\'' +
                '}';
    }
}

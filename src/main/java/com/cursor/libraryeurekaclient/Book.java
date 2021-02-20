package com.cursor.libraryeurekaclient;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Book {

    private long id;
    private String title;
    private boolean inUse;

    public Book(String title) {
        this.title = title;
    }
}


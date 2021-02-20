package com.cursor.libraryeurekaclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library/books")
public class LibraryController {

    @Autowired
    private RestTemplate restTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public List<Book> getAllBooks() {
        List<?> books = restTemplate.getForObject("http://book-db/books", List.class);
        assert books != null;
        return books.stream().map(o -> objectMapper.convertValue(o, Book.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") Long id) {
        return restTemplate.getForObject("http://book-db/books/" + id, Book.class);
    }

    @GetMapping("/in-use")
    public List<Book> getBooksInUse() {
        List<?> objects = restTemplate.getForObject("http://book-db/books", List.class);
        assert objects != null;
        return objects.stream().map(o -> objectMapper.convertValue(o, Book.class))
                .filter(Book::isInUse)
                .collect(Collectors.toList());
    }

    @GetMapping("/in-use/{id}")
    public Book chabgeMarkBookAsInUse(@PathVariable("id") Long id) {
        return restTemplate.getForObject("http://book-db/books/in-use/" + id, Book.class);
    }
}

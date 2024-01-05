package dev.stomrgui.demographql;

import dev.stomrgui.demographql.model.Author;
import dev.stomrgui.demographql.model.Book;
import dev.stomrgui.demographql.model.BookEntity;
import dev.stomrgui.demographql.repository.BookRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//    @QueryMapping
//    public Book bookById(@Argument String id) {
//        return Book.getById(id);
//    }
//
//    @SchemaMapping
//    public Author author(Book book) {
//        return Author.getById(book.authorId());
//    }

    @QueryMapping
    public BookEntity bookById(@Argument Long id) {
        return bookRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}

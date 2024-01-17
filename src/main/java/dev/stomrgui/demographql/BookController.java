package dev.stomrgui.demographql;

import dev.stomrgui.demographql.model.BookEntity;
import dev.stomrgui.demographql.repository.AuthorRepository;
import dev.stomrgui.demographql.repository.BookRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public BookEntity bookById(@Argument Long id) {
        return bookRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @QueryMapping
    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll();
    }

    @MutationMapping
    public BookEntity createBook(@Argument String name, @Argument Long authorId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(RuntimeException::new);

        var book = new BookEntity(name, author);
        return bookRepository.save(book);
    }
}

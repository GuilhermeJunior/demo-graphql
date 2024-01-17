package dev.stomrgui.demographql;

import dev.stomrgui.demographql.model.AuthorEntity;
import dev.stomrgui.demographql.model.BookEntity;
import dev.stomrgui.demographql.repository.AuthorRepository;
import dev.stomrgui.demographql.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.Optional;

@GraphQlTest(BookController.class)
class BookControllerTests {

    @Autowired
    private GraphQlTester graphQlTest;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(
                Optional.of(mockBook())
        );
    }

    @Test
    void shouldGetFirstBook() {
        this.graphQlTest
                .documentName("bookDetails")
                .variable("id", 1L)
                .execute()
                .path("bookById")
                .matchesJson("""
                    {
                        "id": "1",
                        "name": "Effective Java",
                        "author": {
                          "firstName": "Joshua",
                          "lastName": "Bloch"
                        }
                    }
                """);
    }

    private BookEntity mockBook() {
        var author = new AuthorEntity();
        author.setId(1L);
        author.setFirstName("Joshua");
        author.setLastName("Bloch");

        var book = new BookEntity();
        book.setName("Effective Java");
        book.setId(1L);
        book.setAuthor(author);

        return book;
    }
}

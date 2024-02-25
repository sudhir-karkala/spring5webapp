package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.model.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Publisher publisher = new Publisher();
        publisher.setName("ABC Publishing");
        publisher.setAddressLine1("Seattle");
        publisher.setCity("Washington");
        publisher.setState("WA");
        publisher.setZip("20001");
        // save the publisher, else when we save the book, it won't find the publisher resulting in the below error.
        // Caused by: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance -
        // save the transient instance before flushing : guru.springframework.spring5webapp.domain.Book.publisher ->
        // guru.springframework.spring5webapp.domain.Publisher
        publisherRepository.save(publisher);

        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "123123");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        ddd.setPublisher(publisher);
        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisher.getBooks().add(ddd);
        publisherRepository.save(publisher);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "456456");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);
        noEJB.setPublisher(publisher);

        authorRepository.save(rod);
        bookRepository.save(noEJB);
        publisher.getBooks().add(noEJB);
        publisherRepository.save(publisher);


        System.out.println("Started in Bootstrap");
        System.out.println("Number of books:" + bookRepository.count());
        System.out.println("Number of authors:" + authorRepository.count());
        System.out.println("Number of publishers:" + publisherRepository.count());
        System.out.println("Publisher-> no. of books:" + publisher.getBooks().size());
//        System.out.println(ddd.toString());

    }
}

package com.saichethan.SpringBootProject.service;

import com.saichethan.SpringBootProject.dao.BookRepository;
import com.saichethan.SpringBootProject.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository theBookRepository) {
        bookRepository = theBookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Book findById(int theId) {

//        System.out.println("-------------------------------------------------HELLLOOOOOOOOOOOO----------------------------------");
        Optional<Book> result = bookRepository.findById(theId);
//        System.out.println("-------------------------------------------------HELLLOOOOOOOOOOOO----------------------------------");

        Book temp = result.get();

        System.out.println(temp);
        Book theBook = null;

        if (result.isPresent()) {
            theBook = result.get();
        }
        else {
            // we didn't find the Book
            throw new RuntimeException("Did not find Book id - " + theId);
        }

        return theBook;
    }

    @Override
    public void save(Book theBook) {
        bookRepository.save(theBook);
    }

    @Override
    public void deleteById(int theId) {
        bookRepository.deleteById(theId);
    }

}

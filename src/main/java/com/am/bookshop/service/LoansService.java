package com.am.bookshop.service;

import com.am.bookshop.entity.book.BookModel;
import com.am.bookshop.entity.loans.LoansModel;
import com.am.bookshop.repository.BookRepository;
import com.am.bookshop.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LoansService {
    private final BookRepository bookRepository;
    private final LoansRepository loansRepository;

    @Autowired
    public LoansService(BookRepository bookRepository, LoansRepository loansRepository) {
        this.bookRepository = bookRepository;
        this.loansRepository = loansRepository;
    }

    public boolean loanBook(String bookId, String userId) {
        Optional<BookModel> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isPresent() && bookOpt.get().isDisponibilidad()) {
            BookModel book = bookOpt.get();
            book.setDisponibilidad(false);
            bookRepository.save(book);

            LoansModel loan = new LoansModel();
            loan.setBookId(bookId);
            loan.setUserId(userId);
            loan.setPrestarFecha(new Date());
            loansRepository.save(loan);
            return true;
        }
        return false;
    }

    public boolean returnDate(String bookId, String userId) {
        Optional<BookModel> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isPresent()) {
            BookModel book = bookOpt.get();
            Optional<LoansModel> loansOpt = loansRepository.findByBookIdAndUserId(bookId, userId);

            if (loansOpt.isPresent()) {
                LoansModel loan = loansOpt.get();
                book.setDisponibilidad(true);
                bookRepository.save(book);
                loan.setDevolverFecha(new Date());
                loansRepository.save(loan);

                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
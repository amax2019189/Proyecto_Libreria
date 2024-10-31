package com.am.bookshop.repository;

import com.am.bookshop.entity.book.BookModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<BookModel, String> {

}
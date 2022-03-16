package com.netz00.libraryapp.service.impl;

import com.netz00.libraryapp.domain.Author;
import com.netz00.libraryapp.repository.AuthorRepository;
import com.netz00.libraryapp.service.AuthorService;
import com.netz00.libraryapp.service.impl.exception.AuthorDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorDoesNotExistsException(id));
    }

    @Override
    public Author addNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorDoesNotExistsException(id));
        authorRepository.delete(author);
        return author;
    }

    @Override
    public Author patchAuthor(Long id, Author author) {
        Author currentAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorDoesNotExistsException(id));

        if (author.getName() != null)
            currentAuthor.setName(author.getName());

        if (author.getFamily_name() != null)
            currentAuthor.setFamily_name(author.getFamily_name());

        if (author.getBirth_year() != null)
            currentAuthor.setBirth_year(author.getBirth_year());

        if (author.getDeath_year() != null)
            currentAuthor.setDeath_year(author.getDeath_year());

        if (author.getGenre() != null)
            currentAuthor.setGenre(author.getGenre());

        if (author.getNote() != null)
            currentAuthor.setNote(author.getNote());

        return authorRepository.save(currentAuthor);

    }
}

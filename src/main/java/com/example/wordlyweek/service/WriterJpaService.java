package com.example.wordlyweek.service;/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here

import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.model.Writer;
import com.example.wordlyweek.repository.MagazineJpaRepository;
import com.example.wordlyweek.repository.WriterJpaRepository;
import com.example.wordlyweek.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WriterJpaService implements WriterRepository {

    @Autowired
    public WriterJpaRepository writerJpaRepository;
    @Autowired
    public MagazineJpaRepository magazineJpaRepository;

    @Override
    public List<Writer> getAllWriters() {
        return writerJpaRepository.findAll();
    }

    @Override
    public Writer getWriterById(int writerId) {
        try{
            Writer writer=writerJpaRepository.findById(writerId).get();
            return writer;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Writer addWriter(Writer writer) {
        int magazineId = writer.getMagazines().get(0).getMagazineId();
        Magazine magazine;

        try {
            magazine = magazineJpaRepository.findById(magazineId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine not found", e);
        }

        if (magazine == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Magazine not found");
        }

        // Add the writer to the magazine's list of writers
        magazine.getWriters().add(writer);

        // Save the magazine with the updated list of writers
        magazineJpaRepository.save(magazine);

        return writer;


    }

    @Override
    public Writer updateWriter(int writerId, Writer writer) {
        Writer newWriter = writerJpaRepository.findById(writerId).get();
        if (writer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Writer not found");
        }

        // Update the writer's details if provided
        if (writer.getBio() != null) {
            writer.setBio(writer.getBio());
        }

        // Update associations with magazines if provided
        if (writer.getMagazines()!=null) {
            List<Magazine> updatedMagazines = writer.getMagazines();
            for (Magazine magazine : updatedMagazines) {
                int magazineId = magazine.getMagazineId();
                Magazine newMagazine = magazineJpaRepository.findById(magazineId).get();
                if (newMagazine == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Magazine not found");
                }
                newWriter.getMagazines().add(newMagazine);
            }
        }

        // Save and return the updated writer
        return writerJpaRepository.save(newWriter);
    }

    @Override
    public void deleteWriter(int writerId) {
        try {

            writerJpaRepository.deleteById(writerId);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw  new ResponseStatusException(HttpStatus.NO_CONTENT);


    }

    @Override
    public List<Magazine> getWriterMagazines(int writerId) {
        Writer writer = writerJpaRepository.findById(writerId).get();
        if (writer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Writer not found");
        }

        // Return the list of magazines associated with the writer
        return writer.getMagazines();
    }
}



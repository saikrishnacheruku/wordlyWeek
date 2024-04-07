package com.example.wordlyweek.service;

import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.model.Writer;
import com.example.wordlyweek.repository.MagazineJpaRepository;
import com.example.wordlyweek.repository.MagazineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.example.wordlyweek.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MagazineJpaService implements MagazineRepository {

    @Autowired
    private MagazineJpaRepository magazineJpaRepository;

    @Autowired
   private  WriterJpaRepository writerJpaRepository;
    @Override
    public List<Magazine> getAllMagazines() {
        return magazineJpaRepository.findAll();
    }

    @Override
    public Magazine getMagazineById(int magazineId) {
        try{
            Magazine magazine=magazineJpaRepository.findById(magazineId).get();

            return magazine;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Magazine addMagazine(Magazine magazine) {
        int writerId = magazine.getWriters().get(0).getWriterId();
        Writer writer;
        try {
            writer = writerJpaRepository.findById(writerId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Writer not found", e);
        }

        // Set the writer for the magazine
        magazine.getWriters().clear(); // Clear the writers list to avoid duplicates
        magazine.getWriters().add(writer);

        // Save the magazine
        return magazineJpaRepository.save(magazine);
    }

    @Override
    public Magazine updateMagazine(int magazineId, Magazine magazine) {
    try {
        Magazine newMagazine = magazineJpaRepository.findById(magazineId).get();

        if (magazine.getMagazineName() != null) {
            newMagazine.setMagazineName(magazine.getMagazineName());
        }

        if (magazine.getWriters() != null) {
            List<Integer> writersIds = new ArrayList<>();
            for (Writer writer : magazine.getWriters()) {
                writersIds.add(writer.getWriterId());
            }

            List<Writer> writers = writerJpaRepository.findAllById((writersIds));
            newMagazine.setWriters(writers);

        }
        return magazineJpaRepository.save(newMagazine);

    }catch (Exception e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    }

    @Override
    public void deleteMagazine(int magazineId) {
        try {
            magazineJpaRepository.deleteById(magazineId);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Writer> getMagazineWriters(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            return magazine.getWriters();
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


}
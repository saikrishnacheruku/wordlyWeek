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
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MagazineJpaService implements MagazineRepository {

    @Autowired
    private MagazineJpaRepository magazineJpaRepository;

    @Autowired
    private WriterJpaRepository writerJpaRepository;

    @Override
    public List<Magazine> getAllMagazines() {
        return magazineJpaRepository.findAll();
    }

    @Override
    public Magazine getMagazineById(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();

            return magazine;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Magazine addMagazine(Magazine magazine) {
        List<Integer> writerIds = new ArrayList<>();
        for (Writer writer : magazine.getWriters()) {
            writerIds.add(writer.getWriterId());
        }

        List<Writer> writers = writerJpaRepository.findAllById(writerIds);
        magazine.setWriters(writers);

        for (Writer writer : writers) {
            writer.getMagazines().add(magazine);
        }
        Magazine savedMagazine = magazineJpaRepository.save(magazine);
        writerJpaRepository.saveAll(writers);
        return savedMagazine;

    }

    @Override
    public Magazine updateMagazine(int magazineId, Magazine magazine) {
        try {
            Magazine newMagazine = magazineJpaRepository.findById(magazineId).get();

            if (magazine.getMagazineName() != null) {
                newMagazine.setMagazineName(magazine.getMagazineName());
            }
            if (magazine.getPublicationDate() != null) {
                newMagazine.setPublicationDate(magazine.getPublicationDate());
            }

            if (magazine.getWriters() != null) {
                List<Writer> writers = newMagazine.getWriters();
                for (Writer writer : writers) {
                    writer.getMagazines().remove(newMagazine);
                }
                writerJpaRepository.saveAll(writers);
                List<Integer> newWriterIds = new ArrayList<>();
                for (Writer writer : magazine.getWriters()) {
                    newWriterIds.add(writer.getWriterId());

                }
                List<Writer> newWriters = writerJpaRepository.findAllById(newWriterIds);
                for (Writer writer : newWriters) {
                    writer.getMagazines().add(newMagazine);

                }
                writerJpaRepository.saveAll(newWriters);
                newMagazine.setWriters(newWriters);

            }
            newMagazine = magazineJpaRepository.save(newMagazine);

            return newMagazine;

        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void deleteMagazine(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            List<Writer> writers = magazine.getWriters();
            for (Writer writer : writers) {
                writer.getMagazines().remove(magazine);
            }
            writerJpaRepository.saveAll(writers);
            magazineJpaRepository.deleteById(magazineId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Writer> getMagazineWriters(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            return magazine.getWriters();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}
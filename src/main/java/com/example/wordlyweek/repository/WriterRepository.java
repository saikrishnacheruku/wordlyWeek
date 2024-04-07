package com.example.wordlyweek.repository;/*
 * You can use the following import statements
 *
 * import java.util.ArrayList;
 * import java
 
 
 
 */

import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.model.Writer;

import java.util.List;

public interface WriterRepository {

    List<Writer> getAllWriters();

    Writer getWriterById(int writerId);



    Writer addWriter(Writer writer);

    Writer updateWriter(int writerId, Writer writer);

    void deleteWriter(int writerId);

    List<Magazine> getWriterMagazines(int writerId);
}
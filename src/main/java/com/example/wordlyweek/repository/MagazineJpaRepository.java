package com.example.wordlyweek.repository;

import com.example.wordlyweek.model.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineJpaRepository extends JpaRepository<Magazine,Integer> {

}
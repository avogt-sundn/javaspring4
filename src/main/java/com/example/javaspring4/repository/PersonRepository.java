package com.example.javaspring4.repository;

import com.example.javaspring4.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {
}

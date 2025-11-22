package com.example.javaspring4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.javaspring4.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}

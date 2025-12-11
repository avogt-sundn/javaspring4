package com.example.javaspring4;

import com.example.javaspring4.model.Person;
import com.example.javaspring4.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
// @Testcontainers
public class PersonRepositoryIT {

    // @Container
    // static PostgreSQLContainer<?> postgres = new
    // PostgreSQLContainer<>("postgres:17")
    // .withDatabaseName("testdb")
    // .withUsername("test")
    // .withPassword("test");

    // @DynamicPropertySource
    // static void props(DynamicPropertyRegistry r) {
    // r.add("spring.datasource.url", postgres::getJdbcUrl);
    // r.add("spring.datasource.username", postgres::getUsername);
    // r.add("spring.datasource.password", postgres::getPassword);
    // }

    @Autowired
    PersonRepository repo;

    @Test
    void saveAndFind() {
        Person p = new Person("Fred");
        Person saved = repo.save(p);
        assertThat(saved.getId()).isNotNull();

        Person found = repo.findById(saved.getId()).orElseThrow();
        assertThat(found.getName()).isEqualTo("Fred");
    }
}

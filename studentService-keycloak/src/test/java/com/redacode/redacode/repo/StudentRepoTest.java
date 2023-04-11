package com.redacode.redacode.repo;

import com.redacode.redacode.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepoTest {

    @Autowired
    private StudentRepo underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void checkIfEmailExist() {
        //given
        String email="reda@gmail.com";
        Student student = new Student(1L ,"moad", email, "0673782883", "AA82122", "Elbilia", null);
        underTest.save(student);
        //when
        boolean expected = underTest.existsByEmail(email);
        //then
        assertThat(expected).isTrue();
    }
    @Test
    void checkIfEmailDoesntExist() {
        //given
        String email="reda@gmail.com";
        //when
        boolean expected = underTest.existsByEmail(email);
        //then
        assertThat(expected).isFalse();
    }

}

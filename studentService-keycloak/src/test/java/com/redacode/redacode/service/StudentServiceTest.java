package com.redacode.redacode.service;

import com.redacode.redacode.exception.userNotFoundException;
import com.redacode.redacode.model.Student;
import com.redacode.redacode.repo.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.jpa.domain.Specification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepo studentRepo;
    @InjectMocks
    private StudentService underTest;


    /*getting a fresh instance of StudentService*/
    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepo);
    }
    @Test
    void canAddStudent() {
        // Create a new student
        Student newStudent = new Student(25L, "reda", "reda@example.com", null, null, null, null);

        // Set the mock repository to return false for existsByEmail method
        when(studentRepo.existsByEmail("reda@example.com")).thenReturn(false);
//        given(studentRepo.existsByEmail(newStudent.getEmail())).willReturn(false);
        when(studentRepo.save(any(Student.class))).thenReturn(newStudent);
        // Add the new student
        Student addedStudent = underTest.addStudent(newStudent);
        // Verify that the student was added successfully
        assertThat(addedStudent).isNotNull();
        assertEquals(25L, addedStudent.getId());
        assertEquals("reda", addedStudent.getName());
        assertEquals("reda@example.com", addedStudent.getEmail());

        //Set up the mock repo to return true when invoking existsByEmail method
        when(studentRepo.existsByEmail("reda@example.com")).thenReturn(true);

        // try to add the same student again and Verify that the exception was thrown
        assertNull(underTest.addStudent(newStudent));
    }
    /*Check if studentRepo was invoked using findAll Method*/
    @Test
    void canFindAllStudents() {
        //when
        underTest.findAllStudents();
        //then
        verify(studentRepo).findAll();
    }

    @Test
    void canUpdateStudent() {
        //given -> create new student
        Student student = new Student(25L, "reda", "reda@example.com", null, null, null, null);
        //when-> update student
        underTest.updateStudent(student);
        //then -> set an argument capture for the student
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        // verify if the student repo has invoked the save method and capture it
        verify(studentRepo).save(studentArgumentCaptor.capture());
        //get the newValue captured
        Student capturedStudent = studentArgumentCaptor.getValue();
        //compare the newValue with the student created
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void canDeleteStudent() {
        //given
        long studentId = 10;
        // Set the mock repository to return true for existsById method
        when(studentRepo.existsById(studentId)).thenReturn(true);

        //when -> delete Student
        underTest.deleteStudent(studentId);

        //then -> verify if deleteById was invoked by the student repo
        verify(studentRepo).deleteById(studentId);
        //given -> Set the mock repository to return false for existsById method
        when(studentRepo.existsById(studentId)).thenReturn(false);
        //when
        //then -> verify the exception is thrown when invoking deleteStudent method
        assertThatThrownBy(()-> underTest.deleteStudent(studentId))
                .isInstanceOf(userNotFoundException.class)
                .hasMessageContaining("User by id: "+ studentId+ " doesn't exist !");

    }

    @Test
    void canFindStudent() {
        //given
        long studentId = 10;
        Student student = new Student(studentId, "reda", "reda@example.com", null, null, null, null);
        // Set the mock repository to return student for findById method
        when(studentRepo.findById(studentId)).thenReturn(Optional.of(student));

        //when
        //then -> call the findStudent method and assert the result
        assertEquals(student, underTest.findStudent(studentId));
        //given
        when(studentRepo.findById(studentId)).thenReturn(Optional.empty());

        //when
        //then ->  Verify that the exception was thrown by verifying that null was returned
        assertNull(underTest.findStudent(studentId));
    }

    @Test
    public void testGetIdsList() {
        // given
        //create a list of studentIds and a list of expectedStudents
        List<Long> studentIds = Arrays.asList(1L, 2L, 3L);
        List<Student> expectedStudents = Arrays.asList(
                new Student(1L, "reda", null, null, null, null, null),
                new Student(2L, "elias", null, null, null, null, null),
                new Student(3L, "rayan", null, null, null, null, null)
        );
        //when ->Mock the findAll method of studentRepo to return the expected students
        //and for any(specification syntax) This will ensure that the Specification type is matched for the findAll() method call
        when(studentRepo.findAll(any(Specification.class))).thenReturn(expectedStudents);

        // then -> call the getIdsList method and verify the result
        List<Student> actualStudents = underTest.getIdsList(studentIds);
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void assignProfessor() {
        // given -> create a student and a list of professors

        Long studentId = 1L;
        Long profId = 2L;
        List<Long> professors = new ArrayList<>(Arrays.asList(3L, 4L));
        Student student = new Student(studentId, "John",null, null, null, null, professors);

        //when -> Mock the findById of the studentRepo to return a student object and Save to return student

        when(studentRepo.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepo.save(student)).thenReturn(student);

        //call the studentService to assign the profId to the studentId

        Student result = underTest.assignProfessor(studentId, profId);

        //then -> verify that the methods findById and save are invoked one time by the studentRepo Mock

        verify(studentRepo, times(1)).findById(studentId);
        verify(studentRepo, times(1)).save(student);

        //assertThat after assigning the prof to the student the result Matches the list ids of p list;

        List<Long> p = new ArrayList<>(Arrays.asList(2L, 3L, 4L));
        assertEquals(p, result.getProfessors());
    }

}
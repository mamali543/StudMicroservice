package com.redacode.redacode.service;

import com.redacode.redacode.exception.userNotFoundException;
import com.redacode.redacode.model.Student;
import com.redacode.redacode.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //we injected this repository in this class so we can work with it in this class
    private final StudentRepo studentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public Student addStudent(Student student)
    {
        return studentRepo.save(student);
    }

    public List<Student> findAllStudents(){
        return studentRepo.findAll();
    }

    public Student updateStudent(Student student){
        return studentRepo.save(student);
    }

    public void deleteStudent(long id)
    {
        studentRepo.deleteById(id);
    }

    public Student findStudent(long id){
        return studentRepo.findStudentById(id)
                .orElseThrow(()-> new userNotFoundException("User by id:  " + id +" was not found!"));
    }

    public List<Student> getIdsList(List<Long> studentIds) {
        List<Student> students = new ArrayList<Student>();
        for(Long id : studentIds){
            Student p = studentRepo.findById(id).orElse(new Student());
            if(p.getId()==null) continue;
            students.add(p);
        }
        return students;
    }

    public Student assignProfessor(Long studentId, Long profId) {
        Student student = studentRepo.findStudentById(studentId).
                orElseThrow(()-> new userNotFoundException("User by id:  " + studentId +" was not found!"));
        System.out.println(ANSI_RED+ student.getProfessors() + ANSI_RESET);
        List<Long> s = student.getProfessors();
        s.add(profId);
        student.setProfessors(s);
        Set<Long> set = new HashSet<>(student.getProfessors()); // Convert list to Set to remove duplicates
        student.getProfessors().clear();
        student.getProfessors().addAll(set); // Add the distinct values back to the list
        System.out.println(ANSI_BLUE+ student.getProfessors() + ANSI_RESET);
        return studentRepo.save(student);

    }

    public List<Student> addAll(List<Student> students){
        return studentRepo.saveAll(students);
    }
}

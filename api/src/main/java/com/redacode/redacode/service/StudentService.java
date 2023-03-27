package com.redacode.redacode.service;

import com.redacode.redacode.exception.BadRequestException;
import com.redacode.redacode.exception.userNotFoundException;
import com.redacode.redacode.model.Student;
import com.redacode.redacode.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class StudentService {
    public final String ANSI_BLUE = "\u001B[34m";
    public final String ANSI_RESET = "\u001B[0m";
    static final  String ERROR_PREFIX = "Error: ";
    // using format specifiers instead of concatenation
    static final String FORMAT_SPECIFIER_PREFIX = "{}{}{}{}";

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepo studentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public Student addStudent(Student student)
    {
        try {
            boolean existEmail = studentRepo.existsByEmail(student.getEmail());
            if (existEmail) {
                throw new BadRequestException("Email " + student.getEmail() + " Already taken");
            }
             return studentRepo.save(student);
        }catch (BadRequestException x){
            LOGGER.info(FORMAT_SPECIFIER_PREFIX, ANSI_BLUE , ERROR_PREFIX ,x.getMessage() , ANSI_RESET);
            return null;
        }
    }

    public List<Student> findAllStudents(){
        return studentRepo.findAll();
    }

    public Student updateStudent(Student student){
        return studentRepo.save(student);
    }

    public void deleteStudent(long studentId)
    {
        try {
            if (!studentRepo.existsById(studentId))
            {
                throw new userNotFoundException("User by id: "+ studentId+ " doesn't exist !");
            }
            studentRepo.deleteById(studentId);
        }
        catch(userNotFoundException x)
        {
            LOGGER.info(FORMAT_SPECIFIER_PREFIX, ANSI_BLUE , ERROR_PREFIX ,x.getMessage() , ANSI_RESET);
            throw x;
        }
    }

    public Student findStudent(long id){
        try {
            return studentRepo.findById(id)
                    .orElseThrow(() -> new userNotFoundException("User by id:  " + id + " was not found!"));
        } catch(userNotFoundException x)
        {
            LOGGER.info(FORMAT_SPECIFIER_PREFIX, ANSI_BLUE , ERROR_PREFIX ,x.getMessage() , ANSI_RESET);
            return null;
        }
    }

    public List<Student> getIdsList(List<Long> studentIds) {
        return studentRepo.findAll(Specification.where(in("id", studentIds)));
    }

    public static Specification<Student> in(String field, List<?> values) {
        return (root, query, cb) -> root.get(field).in(values);
    }

    public Student assignProfessor(Long studentId, Long profId) {
        Student student = studentRepo.findById(studentId).
                orElseThrow(()-> new userNotFoundException("User by id:  " + studentId +" was not found!"));
        List<Long> s = student.getProfessors();
        s.add(profId);
        student.setProfessors(s);
        Set<Long> set = new HashSet<>(student.getProfessors()); // Convert list to Set to remove duplicates
        student.getProfessors().clear();
        student.getProfessors().addAll(set); // Add the distinct values back to the list
        return studentRepo.save(student);
    }
}

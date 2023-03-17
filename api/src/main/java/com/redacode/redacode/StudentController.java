package com.redacode.redacode;

import com.redacode.redacode.model.Student;
import com.redacode.redacode.repo.StudentRepo;
import com.redacode.redacode.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentService")
@CrossOrigin("*")
public class StudentController {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private final StudentService studentService;
    private final StudentRepo studentRepo;

    public StudentController(StudentService studentService, StudentRepo studentRepo) {
        this.studentService = studentService;
        this.studentRepo = studentRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents(){
        System.out.println("yooo cavaaa");
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/addStudents")
    public List<Student> addStudents(@RequestBody List<Student> students){
        return studentService.addAll(students);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long id){
        Student student = studentService.findStudent(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student newStudent = studentService.addStudent(student);


        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @GetMapping("/get/list")
    public ResponseEntity<List<Student>> getStudentList(@RequestParam List<Long> studentIds){
        System.out.println("yoooooooo");
        List<Student> students = studentService.getIdsList(studentIds);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student newStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.OK);
    }

    @PutMapping("/assign/{studentId}")
    public ResponseEntity<?> assignProf(@PathVariable("studentId") long studentId,@RequestBody Long profId)
    {
        Student newStudent = studentService.assignProfessor(studentId, profId);
        System.out.println(profId);
        System.out.println(ANSI_GREEN+ newStudent.getProfessors() + ANSI_RESET);
        return new ResponseEntity<>(newStudent, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



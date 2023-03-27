package com.redacode.redacode;

import com.redacode.redacode.model.Student;
import com.redacode.redacode.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentService")
@CrossOrigin("*")
public class StudentController {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public List<Student> getAllStudents(){
        return studentService.findAllStudents();
    }
    @GetMapping("/find/{id}")
    public Student getStudent(@PathVariable("id") Long id){
        return studentService.findStudent(id);
    }

    @PostMapping("/add")
    public void addStudent(@RequestBody Student student) {
            studentService.addStudent(student);
    }

    @GetMapping("/get/list")
    public List<Student> getStudentList(@RequestParam List<Long> studentIds){
        return studentService.getIdsList(studentIds);
    }
    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }
    @PutMapping("/assign/{studentId}")
    public Student assignProf(@PathVariable("studentId") long studentId,@RequestBody Long profId)
    {
       return studentService.assignProfessor(studentId, profId);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
    }
}



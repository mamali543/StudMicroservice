package com.redacode.redacode;

import com.redacode.redacode.model.Student;
import com.redacode.redacode.service.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentService")
@CrossOrigin(origins = "*")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<Student> getAllStudents(){
        System.out.println("wesh wesh");
        return studentService.findAllStudents();
    }

    @GetMapping("/find/{id}")
//    @PreAuthorize("hasAnyAuthority('USER')")
    public Student getStudent(@PathVariable("id") Long id){
        return studentService.findStudent(id);
    }

    //to access to this method I have to be authenticated with the role ADMIN
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @GetMapping("/get/list")
//    @PreAuthorize("hasAnyAuthority('USER')")
    public List<Student> getStudentList(@RequestParam List<Long> studentIds){
        System.out.println("reached this point: "+ studentIds);
        return studentService.getIdsList(studentIds);
    }
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }
    @PutMapping("/assign/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Student assignProf(@PathVariable("studentId") long studentId,@RequestBody Long profId)
    {
        System.out.println("Wesh bsse7 ? t2assigna");
       return studentService.assignProfessor(studentId, profId);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
    }
}

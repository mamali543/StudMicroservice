//package com.redacode.redacode;
//import com.redacode.redacode.model.Professor;
//import com.redacode.redacode.model.UserProfAuthObject;
//import com.redacode.redacode.service.IProfessorService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/professors")
//@RequiredArgsConstructor
//public class ProfessorController {
//    private final IProfessorService professorService;
//    @GetMapping("")
//    public List<Professor> getAllProfessors() {
//        UserProfAuthObject authObject = professorService.createAuthObject();
//        String token = professorService.getToken(authObject);
//        List<Professor> professors = professorService.getAll();
//        return professors;
//    }
//
//    @GetMapping("/{ids}")
//    public List<Professor> getProfessorsById(@PathVariable List<String> ids) {
//        UserProfAuthObject authObject = professorService.createAuthObject();
//        String token = professorService.getToken(authObject);
//        List<Professor> professors = professorService.getById(ids);
//        return professors;
//    }
//}
//
//
//

package com.redacode.redacode.repo;

import com.redacode.redacode.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {
    //this is called query method, spring is gping to crate  a query that understand the convention of the name and execute it
    Optional<Student> findStudentById(long id);
}

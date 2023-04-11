package com.redacode.redacode.repo;

import com.redacode.redacode.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepo extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.email = :email")
    boolean existsByEmail(String email);
}

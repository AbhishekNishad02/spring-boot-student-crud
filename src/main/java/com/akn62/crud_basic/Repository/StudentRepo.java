package com.akn62.crud_basic.Repository;

import com.akn62.crud_basic.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Optional<Student> findByIdAndDeletedIsFalse(long id);


    List<Student> findByDeletedIsFalse();
}

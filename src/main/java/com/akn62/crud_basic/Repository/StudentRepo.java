package com.akn62.crud_basic.Repository;

import com.akn62.crud_basic.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
}

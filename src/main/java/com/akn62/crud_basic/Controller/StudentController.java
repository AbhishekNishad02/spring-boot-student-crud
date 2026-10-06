package com.akn62.crud_basic.Controller;


import com.akn62.crud_basic.Entity.Student;
import com.akn62.crud_basic.Services.StudentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/std")
public class StudentController {

    private StudentServices studentServices;
    public StudentController(StudentServices studentServices){
        this.studentServices= studentServices ;
    }

@PostMapping("create")
 public ResponseEntity<Student> create(@RequestBody Student student){


   Student createstd= studentServices.createstudent(student);

   return ResponseEntity
           .status(HttpStatus.CREATED)
           .body(createstd);
 }
}

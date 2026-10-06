package com.akn62.crud_basic.Services;

import com.akn62.crud_basic.Entity.Student;
import com.akn62.crud_basic.Repository.StudentRepo;
import org.springframework.stereotype.Service;

@Service
public class StudentServices {
     private StudentRepo studentRepo;

     public StudentServices(StudentRepo studentRepo){
         this.studentRepo=studentRepo;
     }


    public Student createstudent(Student studentreq){
        Student std = studentRepo.Savestd(studentreq);

        return std;
    }
}

package com.akn62.crud_basic.Services;

import com.akn62.crud_basic.Entity.Student;
import com.akn62.crud_basic.Repository.StudentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServices {
     private StudentRepo studentRepo;

     public StudentServices(StudentRepo studentRepo){
         this.studentRepo=studentRepo;
     }


    public Student createstudent(Student studentreq){
        Student std = studentRepo.save(studentreq);
        return std;
    }
 public Student getstudent(long id){
     Optional<Student> student=studentRepo.findById(id);
     if(student.isPresent()){
         return student.get();
     }
         return null;
 }
 public List<Student> getAllstudent(){
       List<Student> stlist  =studentRepo.findAll();
       return stlist;
 }

 public Student updatestd(long id, Student student){
     Optional<Student> studentreq=studentRepo.findById(id);
     if (studentreq.isEmpty()){
         return null;
     }
  Student savestd=studentreq.get();

     savestd.setName(student.getName());
     savestd.setEmail(student.getEmail());
     savestd.setRoll(student.getRoll());
    savestd.setAge(student.getAge());
    savestd.setSubject(student.getSubject());
    return studentRepo.save(savestd);
 }

 public Boolean deletestudent(long id){
   Boolean isstd =studentRepo.existsById(id);
   if(!isstd) return false;
 studentRepo.deleteById(id);
  return true;
 }
}

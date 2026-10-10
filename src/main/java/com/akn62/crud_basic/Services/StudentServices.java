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
         studentreq.setDeleted(false);
        Student std = studentRepo.save(studentreq);
        return std;
    }
 public Student getstudent(long id){
     Optional<Student> student=studentRepo.findByIdAndDeletedIsFalse(id);
     if(student.isPresent()){
         return student.get();
     }
         return null;
 }
 public List<Student> getAllstudent(){
       List<Student> stdlist  =studentRepo.findByDeletedIsFalse();
       return stdlist;
 }

 public Student updatestd(long id, Student student){
     Optional<Student> studentreq=studentRepo.findByIdAndDeletedIsFalse(id);
     if (studentreq.isEmpty()){
         return null;
     }
  Student savestd=studentreq.get();

     savestd.setName(student.getName());
     savestd.setEmail(student.getEmail());
     savestd.setRoll(student.getRoll());
    savestd.setAge(student.getAge());
    savestd.setSubject(student.getSubject());
    savestd.setDeleted(false);
    return studentRepo.save(savestd);
 }

 public Boolean deletestudent(long id){
   Boolean isstd =studentRepo.existsById(id);
   if(!isstd) return false;
 studentRepo.deleteById(id);
  return true;
 }
 public Boolean deletestudentsoft(long id){
     Optional<Student> exitstudent=studentRepo.findByIdAndDeletedIsFalse(id);
     if(exitstudent.isEmpty()){
         return false;
     }
     Student st=exitstudent.get();
     st.setDeleted(true);
     studentRepo.save(st);
     return true;
 }
}

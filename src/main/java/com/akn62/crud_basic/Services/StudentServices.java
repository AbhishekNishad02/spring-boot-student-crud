package com.akn62.crud_basic.Services;

import com.akn62.crud_basic.Entity.Student;
import com.akn62.crud_basic.Repository.StudentRepo;
import com.akn62.crud_basic.dto.StdRequestDto;
import com.akn62.crud_basic.dto.StdresponseDto;
import com.akn62.crud_basic.dto.Upstdresponsedto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServices {
     private StudentRepo studentRepo;

     public StudentServices(StudentRepo studentRepo){
         this.studentRepo=studentRepo;
     }


    public StdresponseDto createstudent(StdRequestDto studentreq){
         Student student=mapToEntity(studentreq);
        if(studentRepo.count() >= 2){
            throw new RuntimeException("Maximum 2 student allowed ");
        }
         student.setCreatedAt(LocalDateTime.now());
         student.setUpdatedAt(LocalDateTime.now());
         Student stdreq=studentRepo.save(student);
         return mapToDto(stdreq);
    }
 public StdresponseDto getstudent(long id){
     Optional<Student> student=studentRepo.findByIdAndDeletedIsFalse(id);
     if(student.isPresent()){
         return mapToDto(student.get());
     }
         return null;
 }
 public List<StdresponseDto> getAllstudent(){
       List<Student> stdlist  =studentRepo.findByDeletedIsFalse();
       return stdlist.stream()
               .map(this ::mapToDto)
               .toList();
 }

 public Upstdresponsedto updatestd(long id, Upstdresponsedto student){
     Optional<Student> studentreq=studentRepo.findByIdAndDeletedIsFalse(id);
     if (studentreq.isEmpty()){
         return null;
     }
     Student savestd=studentreq.get();
      if(student.getName() !=null) savestd.setName(student.getName());
      if(student.getRoll() != null) savestd.setRoll(student.getRoll());
      if(student.getAge() != null)savestd.setAge(student.getAge());
      if(student.getSubject() != null)savestd.setSubject(student.getSubject());
      savestd.setUpdatedAt(LocalDateTime.now());
    Student saved= studentRepo.save(savestd);
    return mapToUpdateDto(saved);
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
 private  Student mapToEntity(StdRequestDto std){
         Student s1=new Student();
         s1.setName(std.getName());
         s1.setAge(std.getAge());
         s1.setEmail(std.getEmail());
         s1.setRoll(std.getRoll());
         s1.setSubject(std.getSubject());
         s1.setDeleted(false);

     if (s1.getAge() <= 17 || s1.getAge() >= 60) {
         throw new IllegalArgumentException("Age must be greater than 17 and less than 60");
     }
     if(s1.getRoll() > 999){
         throw new RuntimeException("Roll number is Incorrect");
     }
     String s=s1.getEmail();
     if(!s.contains("@")){
         throw new RuntimeException("Enter valid email");
     }
         return s1;
 }
 private StdresponseDto mapToDto(Student std){
         StdresponseDto s1=new StdresponseDto();
         s1.setId(std.getId());
         s1.setName(std.getName());
         s1.setEmail(std.getEmail());
         s1.setRoll(std.getRoll());
         s1.setSubject(std.getSubject());
         s1.setAge(std.getAge());
         s1.setMsg("Student Saved Successfully...");
         s1.setCreate(std.getCreatedAt());
         s1.setUpdate(std.getUpdatedAt());
         return s1;
 }
 private Upstdresponsedto mapToUpdateDto(Student std){
         Upstdresponsedto s1=new Upstdresponsedto();
       s1.setId(std.getId());
       s1.setName(std.getName());
       s1.setEmail(std.getEmail());
       s1.setRoll(std.getRoll());
       s1.setSubject(std.getSubject());
       s1.setAge(std.getAge());
       s1.setMsg("Student Updated Successfully...");
       s1.setUpdate(std.getUpdatedAt());
     return s1;
 }
}

package com.akn62.crud_basic.Services;

import com.akn62.crud_basic.Entity.Student;
import com.akn62.crud_basic.Exception.DuplicateResourceException;
import com.akn62.crud_basic.Exception.ResorcesNotFound;
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
         if(emailExits(student)){
             throw new DuplicateResourceException(" This Email is already Exits..."+student.getEmail());
         }
         Student stdreq=studentRepo.save(student);
         return mapToDto(stdreq);
    }


 public StdresponseDto getstudent(long id){
    Student student=studentRepo.findByIdAndDeletedIsFalse(id)
            .orElseThrow(()->
                    new ResorcesNotFound("Student with id ="+id+"....... Not Found"));
     return mapToDto(student);
 }
 public List<StdresponseDto> getAllstudent(){
       List<Student> stdlist  =studentRepo.findByDeletedIsFalse();
       return stdlist.stream()
               .map(this ::mapToDto)
               .toList();
 }

 public Upstdresponsedto updatestd(long id, Upstdresponsedto student){
     Student std=studentRepo.findByIdAndDeletedIsFalse(id)
             .orElseThrow(() -> new ResorcesNotFound("This Student id is Not Found..."+id));

      if(student.getName() !=null) std.setName(student.getName());
      if(student.getRoll() != null) std.setRoll(student.getRoll());
      if(student.getAge() != null)std.setAge(student.getAge());
      if(student.getSubject() != null)std.setSubject(student.getSubject());
     std.setUpdatedAt(LocalDateTime.now());
    Student saved= studentRepo.save(std);
    return mapToUpdateDto(saved);
 }

 public void deletestudent(long id){
    Student isstd =studentRepo.findById(id).
            orElseThrow(()-> new ResorcesNotFound("This Student id is Not Found..."+id));

 studentRepo.delete(isstd);

 }
 public void deletestudentsoft(long id){
    Student  exitstudent=studentRepo.findByIdAndDeletedIsFalse(id).orElseThrow(
            ()-> new ResorcesNotFound("This Student id is Not Found..."+id)
    );
     exitstudent.setDeleted(true);
     studentRepo.save(exitstudent);

 }
 private  Student mapToEntity(StdRequestDto std){
         Student s1=new Student();
         s1.setName(std.getName());
         s1.setAge(std.getAge());
         s1.setEmail(std.getEmail());
         s1.setRoll(std.getRoll());
         s1.setSubject(std.getSubject());
         s1.setCreatedAt(LocalDateTime.now());
         s1.setUpdatedAt(LocalDateTime.now());
         s1.setDeleted(false);
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

 private boolean emailExits(Student std){
       return   studentRepo.existsByEmail(std.getEmail());
 }
}

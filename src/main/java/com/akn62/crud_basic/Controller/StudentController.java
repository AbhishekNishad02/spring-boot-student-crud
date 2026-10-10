package com.akn62.crud_basic.Controller;


import com.akn62.crud_basic.Entity.Student;
import com.akn62.crud_basic.Services.StudentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
           .status(HttpStatus.CREATED).body(createstd);
 }


 @GetMapping("/get")
 public ResponseEntity<Student> getstudent(@RequestParam long id){
     Student studentreq=studentServices.getstudent(id);
     if(studentreq==null){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
     }
     return ResponseEntity.status(HttpStatus.OK).body(studentreq);
 }


@GetMapping("/getall")
  public ResponseEntity<List<Student>> getallstd() {
    List<Student> stdlist = studentServices.getAllstudent();
    if (stdlist.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok().body(stdlist);

}

@PutMapping("/update")
    public ResponseEntity<Student> update(@RequestParam long id, @RequestBody Student student){
  Student std=studentServices.updatestd(id,student);
    if(std==null){
     return  ResponseEntity.notFound().build();
     }
    return ResponseEntity.status(HttpStatus.OK).body(std);
}

@DeleteMapping("/delete")
    public ResponseEntity <String> delete(@RequestParam long id) {
   Boolean deletestd = studentServices.deletestudent(id);
    if(!deletestd){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok("Record deleted successfully");
}
 @PatchMapping("/soft-delete")
public  ResponseEntity<String> softdelete(@RequestParam long id){
    Boolean deletestdsoft = studentServices.deletestudentsoft(id);
    if(!deletestdsoft){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok("Record deleted successfully");
}
}
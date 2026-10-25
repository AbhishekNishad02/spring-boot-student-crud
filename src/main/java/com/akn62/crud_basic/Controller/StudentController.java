package com.akn62.crud_basic.Controller;


import com.akn62.crud_basic.Entity.Student;
import com.akn62.crud_basic.Services.StudentServices;
import com.akn62.crud_basic.dto.StdRequestDto;
import com.akn62.crud_basic.dto.StdresponseDto;
import com.akn62.crud_basic.dto.Upstdresponsedto;
import jakarta.validation.Valid;
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

@PostMapping
 public ResponseEntity<StdresponseDto> create( @Valid  @RequestBody StdRequestDto studentdto){
   StdresponseDto createstd= studentServices.createstudent(studentdto);
   return ResponseEntity
           .status(HttpStatus.CREATED).body(createstd);
 }


 @GetMapping("/{id}")
 public ResponseEntity<StdresponseDto> getstudent(@PathVariable long id){
     StdresponseDto studentreq=studentServices.getstudent(id);
     return ResponseEntity.ok().body(studentreq);
 }


@GetMapping
  public ResponseEntity<List<StdresponseDto>> getallstd() {
    List<StdresponseDto> stdlist = studentServices.getAllstudent();
    return ResponseEntity.ok().body(stdlist);

}

@PutMapping
    public ResponseEntity<Upstdresponsedto> update(@RequestParam long id, @RequestBody Upstdresponsedto student){
        if(student.getEmail() != null){
            return ResponseEntity.badRequest().build();
        }
    Upstdresponsedto std=studentServices.updatestd(id,student);
     return ResponseEntity.status(HttpStatus.OK).body(std);
}

@DeleteMapping("/{id}")
    public ResponseEntity <String> delete(@PathVariable long id) {
    studentServices.deletestudent(id);
    return ResponseEntity.noContent().build();
}

 @PatchMapping("/soft-delete")
public  ResponseEntity<String> softdelete(@RequestParam long id){
     studentServices.deletestudentsoft(id);
    return ResponseEntity.noContent().build();
}
}
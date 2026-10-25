package com.akn62.crud_basic.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StdRequestDto {
    @NotBlank(message = "Name Cannot be Null and Empty")
    @Size(min=2,max = 50,message = "Student name must be 2 to 50 Character long")
    private String name;

     @Email(message = "Please Enter Valid Email")
     private String email;

     @Min(value = 18,message = "Student must be eligible 18 year old")
     @Max(value = 90,message = "please enter your valid Age")
     @NotNull (message = "Age is Required")
    private Integer age;

    @Max(value = 999,message = "Enter Roll No is Unvalid")
    @NotNull(message = "Roll No. is not null")
    private Integer roll;

    @NotBlank(message = "Subject is NOt Null or Blank Value")
    private String subject;
}

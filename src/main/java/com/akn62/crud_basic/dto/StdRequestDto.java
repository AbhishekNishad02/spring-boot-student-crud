package com.akn62.crud_basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StdRequestDto {
    private String name;
    private String email;
    private Integer age;
    private Integer roll;
    private String subject;
}

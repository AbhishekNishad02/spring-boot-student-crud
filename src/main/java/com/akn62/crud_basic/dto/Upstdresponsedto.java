package com.akn62.crud_basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Upstdresponsedto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Integer roll;
    private String subject;
    private LocalDateTime update;
    private String msg;

}

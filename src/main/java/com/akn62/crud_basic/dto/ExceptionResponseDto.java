package com.akn62.crud_basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDto {
    private LocalDateTime dateTime;
    private int statuscode;
    private String  error;
    private String msg;
    private String path;
}

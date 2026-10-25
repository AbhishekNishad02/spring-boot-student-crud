package com.akn62.crud_basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationExceptionResponseDto {
    private LocalDateTime dateTime;
    private int statuscode;
    private String  error;
    private String  msg;
    private String path;
    private Map<String ,String > feilderror;
}

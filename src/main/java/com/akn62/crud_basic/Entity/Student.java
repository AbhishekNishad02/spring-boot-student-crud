package com.akn62.crud_basic.Entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
        @Id
        private long id;

        private String name;
        private String email;
        private int age;
        private int roll;
        private String subject;

    }



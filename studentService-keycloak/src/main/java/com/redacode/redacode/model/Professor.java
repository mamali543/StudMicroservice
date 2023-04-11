package com.redacode.redacode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor {
    private Long id;

    private String name;
    private String cin;
    private String subject;
    private String email;
    private String telephone;
}

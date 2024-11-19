package com.system.creditOnline.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.system.creditOnline.utils.CustomLocalDateDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthdate;
    private Integer creditLine;

}

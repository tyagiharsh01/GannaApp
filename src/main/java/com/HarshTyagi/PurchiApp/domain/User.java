package com.HarshTyagi.PurchiApp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class User {
    private String firstName;
    private String lastName;
    @Id
    private String email;
    private String password;
}

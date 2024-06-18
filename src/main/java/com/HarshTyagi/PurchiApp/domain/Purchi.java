package com.HarshTyagi.PurchiApp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class Purchi {
    private String purchiHolderName;
    private Date date;
    private double weight;
    private double rate;
    private String sugarMillName;
    private String troliHolder;
    private String email;
    @Id
    private String id;

}

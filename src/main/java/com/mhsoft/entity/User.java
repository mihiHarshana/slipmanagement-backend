package com.mhsoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class User {
    @Id  @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long userid;
    private String username;
    @JsonIgnore
    private String password;
    private String userstatus;
    private String usertype;

}


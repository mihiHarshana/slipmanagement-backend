package com.mhsoft.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest implements Serializable {
    String username;
    String password;

    private static final long serialVersionUID = 5926468583005150707L;
}



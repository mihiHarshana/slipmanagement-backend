package com.mhsoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class UserDTO {
    private long userid;
    private String username;
    private String password;

    private String userstatus;
    private String usertype;

}

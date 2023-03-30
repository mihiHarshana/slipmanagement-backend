package com.mhsoft.controller;

import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.dto.UserDTO;
import com.mhsoft.entity.User;
import com.mhsoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveUser(@RequestBody UserDTO userDTO)
    {
        return userService.saveUser(userDTO);
    }

    @RequestMapping(value="/login3" , method = RequestMethod.POST)
    public String athenticateUser(@RequestBody User userDTO) throws Exception {
        User userlog = userService.getUser(userDTO);
        authenticate(userlog);
        return userService.loginUser(userlog);

    }
    @RequestMapping(value="/update", produces = "application/json" , method = RequestMethod.POST)
    public String updateUser(@RequestBody UserDTO userDTO) throws Exception {

        return userService.updateUser(userDTO);
    }

    private void authenticate(User user) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }



}

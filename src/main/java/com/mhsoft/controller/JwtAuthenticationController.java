package com.mhsoft.controller;

import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.model.DAOUser;
import com.mhsoft.model.JwtRequest;
import com.mhsoft.model.UserDTO;
import com.mhsoft.repo.UserRepo;
import com.mhsoft.service.JwtUserDetailsService;
import com.mhsoft.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepo userRepo;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        Utils utils = new Utils();
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);
            DAOUser daoUser = userRepo.getUserByUserName(authenticationRequest.getUsername());

            if (daoUser != null) {
                if (daoUser.getUserStatus().equals("APPROVED")) {
                    JSONObject jo = new JSONObject();
                    jo.put("token", token);
                    jo.put("mobile", daoUser.getUsername());
                    jo.put("logintime", System.currentTimeMillis());
                    jo.put("usertype", daoUser.getUsertype());
                    jo.put("userstatus", daoUser.getUserStatus());

                    return jo.toString();
                } else if (daoUser.getUserStatus().equals("PENDING")) {
                    return utils.JsonMessage("User Not approved", HttpStatus.NOT_ACCEPTABLE);
                } else if (daoUser.getUserStatus().equals("SUSPENDED")) {
                    return utils.JsonMessage("User is Suspended", HttpStatus.NOT_ACCEPTABLE);
                }else if (daoUser.getUserStatus().equals("DEACTIVATED")) {
                    return utils.JsonMessage("User Deactivated", HttpStatus.NOT_ACCEPTABLE);
                }

                else  {
                    return utils.JsonMessage("Unknow status ", HttpStatus.NOT_ACCEPTABLE);
                }

            } else {
                return utils.JsonMessage("User Not available", HttpStatus.NOT_ACCEPTABLE);
            }

        } catch (Exception e) {

            return utils.JsonMessage("Invalid Credentails", HttpStatus.ACCEPTED);
        }

    }

    /*
     * @RequestMapping(value = "/register", method = RequestMethod.POST) public
     * ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
     * return ResponseEntity.ok(userDetailsService.save(user)); }
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveUser(@RequestBody UserDTO user) {
        String jo = userDetailsService.save(user);

        return jo;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
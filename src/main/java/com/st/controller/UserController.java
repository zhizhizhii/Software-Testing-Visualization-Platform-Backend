package com.st.controller;

import com.st.request.ChangePasswordRequest;
import com.st.request.UserRequest;
import com.st.service.UserService;
import com.st.view.Status;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(path="/api/authorization")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(path="/register")
    @ResponseBody
    public Status register(@RequestBody UserRequest userRequest, HttpServletResponse response){
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        return userService.register(username,password);
    }

    @PostMapping(path="/login")
    @CrossOrigin(value = "http://localhost:8080", maxAge = 1800, allowedHeaders = "*")
    @ResponseBody
    public Status login(@RequestBody UserRequest userRequest, HttpServletResponse response){
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        return userService.login(username,password,response);
    }

    @PostMapping(path="/changepassword")
    @ResponseBody
    public Status changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest request){
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        return userService.changePassword(oldPassword,newPassword,request);
    }
}

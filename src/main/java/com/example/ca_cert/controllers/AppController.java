package com.example.ca_cert.controllers;

import com.example.ca_cert.businesslogic.DataBaseUtility;
import com.example.ca_cert.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AppController {
    @Autowired
    DataBaseUtility dataBaseUtility;
    @GetMapping("/")
    public void getMethod()
    {

    }
    @PostMapping("/registeruser")
    public void registerUser(@RequestBody UserData userData)
    {
dataBaseUtility.save(userData);
        System.out.println("Hari Om"+userData.getAadharcard());
    }
}

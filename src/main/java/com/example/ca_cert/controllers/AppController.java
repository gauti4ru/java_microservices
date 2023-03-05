package com.example.ca_cert.controllers;

import com.example.ca_cert.advice.AppException;
import com.example.ca_cert.businesslogic.DataBaseUtility;
import com.example.ca_cert.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> registerUser(@RequestBody UserData userData)
    {
        if(String.valueOf(userData.getAadharcard()).length()!=12)
        {
            String errorMessage="Aadhar card is incorrect";
            throw new AppException(errorMessage);
        }
        else
        {
            dataBaseUtility.saveAndFlush(userData);
            System.out.println(+userData.getAadharcard());
            return new ResponseEntity<>("Created", HttpStatus.OK);
        }
    }

    @DeleteMapping("/registeruser")
    public void DeleteUser(@RequestBody UserData userData)
    {
        dataBaseUtility.deleteById(userData.getAadharcard());
        System.out.println(+userData.getAadharcard());
    }
}

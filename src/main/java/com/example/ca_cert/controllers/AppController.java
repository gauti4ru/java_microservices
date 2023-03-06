package com.example.ca_cert.controllers;

import com.example.ca_cert.advice.AppException;
import com.example.ca_cert.businesslogic.DataBaseUtility;
import com.example.ca_cert.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/know")
public class AppController {
    @Autowired
    DataBaseUtility dataBaseUtility;

    @GetMapping("/user")
    public Optional<UserData> getMethod(@RequestParam(name = "aadharnumber") Long aadharnumber) {
        return dataBaseUtility.findById(aadharnumber);

    }

    @PostMapping("/user")
    public ResponseEntity<String> registerUser(@RequestBody UserData userData) {
        if (String.valueOf(userData.getAadharcard()).length() != 12) {
            String errorMessage = "Aadhar card is incorrect";
            throw new AppException(errorMessage);
        } else {
            dataBaseUtility.saveAndFlush(userData);
            System.out.println(+userData.getAadharcard());
            return new ResponseEntity<>("Created", HttpStatus.OK);
        }
    }

    @DeleteMapping("/user")
    public void DeleteUser(@RequestBody UserData userData) {
        dataBaseUtility.deleteById(userData.getAadharcard());
        System.out.println(+userData.getAadharcard());
    }
}

package com.example.ca_cert;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/")
    public DemoData getMethod()
    {
        DemoData obj = new DemoData();
        obj.setName("hari");
        obj.setAge(25);
        return obj;
    }
}

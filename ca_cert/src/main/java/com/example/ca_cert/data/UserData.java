package com.example.ca_cert.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserBasicInfo")
public class UserData {
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;

    @Id
    @Column(name = "aadharcard")
    private Long aadharcard;
}

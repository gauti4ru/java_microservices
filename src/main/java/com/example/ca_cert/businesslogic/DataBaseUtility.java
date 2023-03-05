package com.example.ca_cert.businesslogic;

import com.example.ca_cert.data.UserData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataBaseUtility extends JpaRepository<UserData,Long> {

}

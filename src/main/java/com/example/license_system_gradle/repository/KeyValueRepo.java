package com.example.license_system_gradle.repository;


import com.example.license_system_gradle.Model.KeyValuePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyValueRepo extends JpaRepository<KeyValuePair,String> {

}

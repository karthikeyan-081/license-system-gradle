package com.example.license_system_gradle.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "key_value_pair")
public class KeyValuePair {

    @Id
    @Column(name = "k")
    private String key;
    private String value;
}

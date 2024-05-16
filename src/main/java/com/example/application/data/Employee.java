package com.example.application.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Employee extends AbstractEntity {


    @NotBlank
    private String name;

//    @OneToMany(mappedBy = "employee")
//    @Nullable
//    private List<TimeEntry> timeEntries = new ArrayList<>();

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }


}

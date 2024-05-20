package com.example.application.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project extends AbstractEntity {
    private String name;


//
//    @OneToMany(mappedBy = "project")
//    @Nullable
//    private List<TimeEntry> timeEntries = new ArrayList<>();

    public Project() {
    }
    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

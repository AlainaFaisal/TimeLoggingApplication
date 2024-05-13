package com.example.application.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project extends AbstractEntity {
    @NotBlank
    private String name;


    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//
//    public List<TimeEntry> timeEntries() {
//        return timeEntries;
//    }
//
//    public void setprojects(List<TimeEntry> timeEntries) {
//        this.timeEntries = timeEntries;
//    }
}

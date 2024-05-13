package com.example.application.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TimeEntry extends AbstractEntity {


    private LocalDate date2;

    private LocalTime arrivalTime;

    private String timeCategory;

    private LocalTime departureTime;

    private Duration breakDuration;

    private Double hours;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @NotNull
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @NotNull
    private Project project;


    public String getTimeCategory() {return timeCategory;}

    public void setTimeCategory(String timeCategory) {this.timeCategory = timeCategory;}

    public LocalTime getArrivalTime() {return arrivalTime;}

    public void setArrivalTime(LocalTime arrivalTime) {this.arrivalTime = arrivalTime;}

    public LocalTime getDepartureTime() {return departureTime;}

    public void setDepartureTime(LocalTime departureTime) {this.departureTime = departureTime;}

    public Duration getBreakDuration() {return breakDuration;}
    public void setBreakDuration(Duration breakDuration) {this.breakDuration = breakDuration;}

    public Double getHours() {
        long minutesWorked = Duration.between(arrivalTime, departureTime).minus(breakDuration).toMinutes();
        return hours = (minutesWorked / 60.0); // Convert minutes to hours
    }

    public void setHours(Double hours) {this.hours = hours;}


    public LocalDate getDate() {
        return date2;
    }

    public void setDate(LocalDate date2) {this.date2 = date2;}

    public Employee getEmployee() {return employee;}

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
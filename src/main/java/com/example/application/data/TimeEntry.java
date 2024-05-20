package com.example.application.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class TimeEntry extends AbstractEntity {

    private LocalDate date2;

    private LocalTime arrivalTime;

    private String timeCategory;

    private LocalTime departureTime;

    private Duration breakDuration;

    private Double hours;
    private String description;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @NotNull
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
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
        return hours = (minutesWorked / 60.0);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


//
//    @Transactional
//    public TimeEntry createTimeEntry(LocalDate date, LocalTime arrivalTime, LocalTime departureTime,
//                                     Duration breakDuration, Double hours, String timeCategory,
//                                     String employeeName, String projectName) {
//        TimeEntry timeEntry = new TimeEntry();
//        timeEntry.setDate(date);
//        timeEntry.setArrivalTime(arrivalTime);
//        timeEntry.setDepartureTime(departureTime);
//        timeEntry.setBreakDuration(breakDuration);
//        timeEntry.setHours(hours);
//        timeEntry.setTimeCategory(timeCategory);
//
//        Employee employee = findOrCreateEmployee(employeeName);
//        Project project = findOrCreateProject(projectName);
//
//        timeEntry.setEmployee(employee);
//        timeEntry.setProject(project);
//
//        entityManager.persist(timeEntry);
//        return timeEntry;
//    }
//
//    private Employee findOrCreateEmployee(String name) {
//        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.name = :name", Employee.class)
//                .setParameter("name", name)
//                .getResultList();
//        if (employees.isEmpty()) {
//            Employee newEmployee = new Employee();
//            newEmployee.setName(name);
//            entityManager.persist(newEmployee);
//            return newEmployee;
//        }
//        return employees.get(0);
//    }
//
//    private Project findOrCreateProject(String name) {
//        List<Project> projects = entityManager.createQuery("SELECT p FROM Project p WHERE p.name = :name", Project.class)
//                .setParameter("name", name)
//                .getResultList();
//        if (projects.isEmpty()) {
//            Project newProject = new Project();
//            newProject.setName(name);
//            entityManager.persist(newProject);
//            return newProject;
//        }
//        return projects.get(0);
//    }
}

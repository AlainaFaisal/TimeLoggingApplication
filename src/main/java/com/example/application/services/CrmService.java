package com.example.application.services;

import com.example.application.data.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CrmService {

    private final TimeEntryRepository timeRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public CrmService(TimeEntryRepository timeRepository,
                      ProjectRepository projectRepository,
                      EmployeeRepository employeeRepository) {
        this.timeRepository = timeRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<TimeEntry> findAllProjectsforanEmployee(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return timeRepository.findAll();
        } else {
            return timeRepository.search(stringFilter);
        }
    }

    public long countProjects() {
        return projectRepository.count();
    }

    public long countEmployees() {
        return employeeRepository.count();
    }

    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    public void saveProject(Project project) {
        if (project == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        projectRepository.save(project);
    }

    public void saveTimeEntry(TimeEntry timeEntry) {
        if (timeEntry == null) {
            System.err.println("TimeEntry is null. Are you sure you have connected your form to the application?");
            return;
        }
         timeRepository.save(timeEntry);
    }

    public void deleteTimeEntry(TimeEntry timeEntry) {
         timeRepository.delete(timeEntry);
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }
    public List<String> findAllDistinctTimeEntries() {
        return timeRepository.findDistinctTime();
    }
    public List<TimeEntry> findAllTimeEntries() {
        return timeRepository.findAll();
    }

    public TimeEntry createTimeEntry(LocalDate date, LocalTime arrivalTime, LocalTime departureTime,
                                     Duration breakDuration, Double hours, String timeCategory,
                                     String employeeName, String projectName) {
        Employee employee = findOrCreateEmployee(employeeName);
        Project project = findOrCreateProject(projectName);

        TimeEntry timeEntry2 = new TimeEntry();
        timeEntry2.setDate(date);
        timeEntry2.setArrivalTime(arrivalTime);
        timeEntry2.setDepartureTime(departureTime);
        timeEntry2.setBreakDuration(breakDuration);
        timeEntry2.setHours(hours);
        timeEntry2.setTimeCategory(timeCategory);
        timeEntry2.setEmployee(employee);
        timeEntry2.setProject(project);

        return timeRepository.save(timeEntry2);
    }

    private Employee findOrCreateEmployee(String name) {
        return employeeRepository.findByName(name)
                .orElseGet(() -> {
                    Employee newEmployee = new Employee();
                    newEmployee.setName(name);
                    return employeeRepository.save(newEmployee);
                });
    }

    private Project findOrCreateProject(String name) {
        return projectRepository.findByName(name)
                .orElseGet(() -> {
                    Project newProject = new Project();
                    newProject.setName(name);
                    return projectRepository.save(newProject);
                });
    }

}
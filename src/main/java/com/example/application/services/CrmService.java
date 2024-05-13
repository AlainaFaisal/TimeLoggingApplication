package com.example.application.services;

import com.example.application.data.*;
import org.springframework.stereotype.Service;

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
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
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


}
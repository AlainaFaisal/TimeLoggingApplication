package com.example.application.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    @Query("SELECT te FROM TimeEntry te " +
            "WHERE lower(te.employee.name) LIKE lower(concat('%', :searchTerm, '%'))")
    List<TimeEntry> search(@Param("searchTerm") String searchTerm);

    @Query("SELECT DISTINCT a.timeCategory FROM TimeEntry a")
    List<String> findDistinctTime();

    // Add this method to find TimeEntries by Employee
    List<TimeEntry> findByEmployee(Employee employee);
}

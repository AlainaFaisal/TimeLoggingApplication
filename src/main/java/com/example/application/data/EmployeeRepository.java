package com.example.application.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    @Query("select c from Contact c " +
//            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) ")
//    List<Employee> search(@Param("searchTerm") String searchTerm);
Optional<Employee> findByName(String name);

}

package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Interface for employee resource operations.
 */
public interface EmployeeResource {

    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param id employee id.
     * @return {@link Employee} resource.
     */
    @RequestMapping("/v1/bfs/employees/{id}")
    ResponseEntity<Employee> employeeGetById(@PathVariable("id") int id);

    /**
     * Saves the {@link Employee} resource and assign id to it.
     *
     * @param employee employee details.
     * @return {@link Employee} resource.
     */
    @PostMapping("/v1/bfs/employees")
    ResponseEntity<Employee> addEmployee(@RequestHeader("idempotence-key") String idempotenceKey, @Valid @RequestBody Employee employee);

}

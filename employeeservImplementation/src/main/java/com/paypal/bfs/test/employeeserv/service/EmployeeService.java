package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeDB;
import com.paypal.bfs.test.employeeserv.exception.DataNotFoundException;
import com.paypal.bfs.test.employeeserv.mapper.EmployeeMapper;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapperImpl;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapperImpl) {
        this.employeeRepository = employeeRepository;
        this.employeeMapperImpl = employeeMapperImpl;
    }

    public Employee fetchEmployee(int id) {
        try {
            LOGGER.info("Fetching employee details for id: {}", id);
            EmployeeDB employeeDB = employeeRepository.getOne(id);
            return employeeMapperImpl.mapEmployee(employeeDB);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Employee with id: {} is not available", id);
            throw new DataNotFoundException("Employee with id: " + id + " is not available", e);
        }
    }

    @Cacheable(value = "employees", key = "#idempotenceKey")
    public Employee saveEmployee(String idempotenceKey, Employee employee) {
        LOGGER.info("Adding new employee in database");
        EmployeeDB employeeDB = employeeMapperImpl.mapEmployeeDB(employee);
        EmployeeDB response = employeeRepository.save(employeeDB);
        LOGGER.info("New employee added in database");
        return employeeMapperImpl.mapEmployee(response);
    }

}

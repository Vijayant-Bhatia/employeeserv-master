package com.paypal.bfs.test.employeeserv;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressDB;
import com.paypal.bfs.test.employeeserv.entity.EmployeeDB;

import java.time.LocalDate;

public class EmployeeTestData {

    public static EmployeeDB getEmployeeDB(int id) {
        EmployeeDB employeeDB = new EmployeeDB();
        employeeDB.setFirstName("Vijayant");
        employeeDB.setLastName("Bhatia");
        employeeDB.setId(id);
        AddressDB address = new AddressDB();
        address.setLine1("k-103");
        address.setLine2("Rose Valley");
        address.setCity("Pune");
        address.setState("MH");
        address.setCountry("India");
        address.setZipCode("411027");
        employeeDB.setAddress(address);
        employeeDB.setDateOfBirth(LocalDate.of(1989, 1, 8));
        return employeeDB;
    }

    public static Employee getEmployee(Integer id) {
        Employee employee = new Employee();
        employee.setFirstName("Vijayant");
        employee.setLastName("Bhatia");
        employee.setId(id);
        Address address = new Address();
        address.setLine1("k-103");
        address.setLine2("Rose Valley");
        address.setCity("Pune");
        address.setState("MH");
        address.setCountry("India");
        address.setZipCode("411027");
        employee.setAddress(address);
        employee.setDateOfBirth("08-01-1989");
        return employee;
    }
}

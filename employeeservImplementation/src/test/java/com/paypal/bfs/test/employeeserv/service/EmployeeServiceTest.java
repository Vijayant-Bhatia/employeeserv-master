package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressDB;
import com.paypal.bfs.test.employeeserv.entity.EmployeeDB;
import com.paypal.bfs.test.employeeserv.exception.DataNotFoundException;
import com.paypal.bfs.test.employeeserv.mapper.EmployeeMapperImpl;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static com.paypal.bfs.test.employeeserv.EmployeeTestData.getEmployee;
import static com.paypal.bfs.test.employeeserv.EmployeeTestData.getEmployeeDB;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    public static final int ID = 1;
    @Mock
    private EmployeeMapperImpl employeeMapperImpl;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldFetchEmployee() {
        //given

        //when
        EmployeeDB employeeDB = getEmployeeDB(ID);
        when(employeeRepository.getOne(ID)).thenReturn(employeeDB);
        when(employeeMapperImpl.mapEmployee(employeeDB)).thenCallRealMethod();
        when(employeeMapperImpl.mapAddress(Mockito.any(AddressDB.class))).thenCallRealMethod();

        Employee employee = employeeService.fetchEmployee(ID);

        //then
        assertThat(employee, allOf(
                hasProperty("id", equalTo(ID)),
                hasProperty("firstName", equalTo(employeeDB.getFirstName())),
                hasProperty("lastName", equalTo(employeeDB.getLastName())),
                hasProperty("dateOfBirth", equalTo("08-01-1989")),
                hasProperty("address", allOf(
                        hasProperty("line1", equalTo(employeeDB.getAddress().getLine1())),
                        hasProperty("line2", equalTo(employeeDB.getAddress().getLine2())),
                        hasProperty("city", equalTo(employeeDB.getAddress().getCity())),
                        hasProperty("state", equalTo(employeeDB.getAddress().getState())),
                        hasProperty("country", equalTo(employeeDB.getAddress().getCountry())),
                        hasProperty("zipCode", equalTo(employeeDB.getAddress().getZipCode()))
                        )
                )));

    }

    @Test
    void shouldThrowExceptionIfEmployeeIsNotPresent() {
        //given
        int id = ID;

        //when
        when(employeeRepository.getOne(id)).thenThrow(new EntityNotFoundException());

        //then
        Assertions.assertThrows(DataNotFoundException.class, () -> employeeService.fetchEmployee(id));

    }

    @Test
    void shouldSaveEmployee() {
        Employee employee = getEmployee(ID);
        when(employeeMapperImpl.mapEmployeeDB(employee)).thenCallRealMethod();
        when(employeeMapperImpl.mapAddressDB(Mockito.any(Address.class))).thenCallRealMethod();
        when(employeeMapperImpl.mapEmployee(Mockito.any(EmployeeDB.class))).thenCallRealMethod();
        when(employeeMapperImpl.mapAddress(Mockito.any(AddressDB.class))).thenCallRealMethod();
        EmployeeDB employeeDB = getEmployeeDB(ID);
        when(employeeRepository.save(Mockito.any(EmployeeDB.class))).thenReturn(employeeDB);

        String idempotenceKey = "idempotenceKey";
        Employee response = employeeService.saveEmployee(idempotenceKey, employee);
        //then
        assertThat(response, allOf(
                hasProperty("id", equalTo(ID)),
                hasProperty("firstName", equalTo(employee.getFirstName())),
                hasProperty("lastName", equalTo(employee.getLastName())),
                hasProperty("dateOfBirth", equalTo("08-01-1989")),
                hasProperty("address", allOf(
                        hasProperty("line1", equalTo(employee.getAddress().getLine1())),
                        hasProperty("line2", equalTo(employee.getAddress().getLine2())),
                        hasProperty("city", equalTo(employee.getAddress().getCity())),
                        hasProperty("state", equalTo(employee.getAddress().getState())),
                        hasProperty("country", equalTo(employee.getAddress().getCountry())),
                        hasProperty("zipCode", equalTo(employee.getAddress().getZipCode()))
                        )
                )));
    }

}
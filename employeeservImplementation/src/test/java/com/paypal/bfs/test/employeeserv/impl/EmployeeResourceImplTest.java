package com.paypal.bfs.test.employeeserv.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.DataNotFoundException;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.paypal.bfs.test.employeeserv.EmployeeTestData.getEmployee;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeResourceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void shouldReturnEmployeeWithHttpStatusOK() throws Exception {
        int id = 1;
        Employee employee = getEmployee(id);

        //when
        when(employeeService.fetchEmployee(id)).thenReturn(employee);

        //then
        this.mockMvc.perform(get("/v1/bfs/employees/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.first_name").value(employee.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(employee.getLastName()));
    }

    @Test
    public void shouldReturnErrorWhenEmployeeNotFoundWithHttpStatusNOT_FOUND() throws Exception {
        int id = 1;
        String errorMessage = "Employee with id: " + id + " is not available";

        //when
        when(employeeService.fetchEmployee(id)).thenThrow(new DataNotFoundException(errorMessage, null));

        //then
        this.mockMvc.perform(get("/v1/bfs/employees/{id}", id)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    void shouldAddEmployee() throws Exception {

        //given
        Employee employee = getEmployee(1);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(employee);

        String idempotenceKey = "123e4567-e89b-12d3-a456-426655440010";

        //when
        when(employeeService.saveEmployee(anyString(), any(Employee.class))).thenReturn(employee);

        //then
        this.mockMvc.perform(post("/v1/bfs/employees")
                .header("idempotence-key", idempotenceKey)
                .contentType(APPLICATION_JSON_UTF8)
                .content(s))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.first_name").value(employee.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(employee.getLastName()));

    }

    @Test
    void shouldThrowExceptionIfRequiredFieldsAreMissingWhileAddingNewEmployee() throws Exception {

        //given
        Employee employee = getEmployee(1);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(employee);

        String idempotenceKey = "123e4567-e89b-12d3-a456-426655440010";

        //when
        when(employeeService.saveEmployee(anyString(), any(Employee.class))).thenReturn(employee);

        //then
        this.mockMvc.perform(post("/v1/bfs/employees")
                .header("idempotence-key", idempotenceKey)
                .contentType(APPLICATION_JSON_UTF8)
                .content(s))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.first_name").value(employee.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(employee.getLastName()));
    }

}
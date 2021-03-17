package com.paypal.bfs.test.employeeserv;

import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeservApplicationTest {


    @Autowired
    private EmployeeResourceImpl controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }


}
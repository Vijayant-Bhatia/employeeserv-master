/*
package com.paypal.bfs.test.employeeserv.repository;

import com.paypal.bfs.test.employeeserv.EmployeeTestData;
import com.paypal.bfs.test.employeeserv.entity.EmployeeDB;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EmployeeRepositoryTest {

    private static final int ID = 1;
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldReturnEmployeeWhenFindById() {
        // given
        EmployeeDB employee = EmployeeTestData.getEmployeeDB(ID);
        entityManager.persist(employee.getAddress());
        entityManager.persist(employee);
        entityManager.flush();

        // when
        EmployeeDB result = employeeRepository.getOne(ID);

        // then
        assertThat(result, allOf(
                hasProperty("id", equalTo(ID)),
                hasProperty("firstName", equalTo(employee.getFirstName())),
                hasProperty("lastName", equalTo(employee.getLastName()))));
    }


}*/

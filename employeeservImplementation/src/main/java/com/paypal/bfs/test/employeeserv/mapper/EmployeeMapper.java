package com.paypal.bfs.test.employeeserv.mapper;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressDB;
import com.paypal.bfs.test.employeeserv.entity.EmployeeDB;
import org.mapstruct.*;

@Mapper(componentModel = "Spring")
public interface EmployeeMapper {

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "dd-MM-yyyy")
    Employee mapEmployee(EmployeeDB employeeDB);

    @InheritInverseConfiguration
    EmployeeDB mapEmployeeDB(Employee employee);

    Address mapAddress(AddressDB addressDB);

    @InheritInverseConfiguration
    AddressDB mapAddressDB(Address address);

}

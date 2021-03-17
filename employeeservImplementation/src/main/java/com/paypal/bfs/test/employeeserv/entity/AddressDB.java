package com.paypal.bfs.test.employeeserv.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class AddressDB {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int addressId;

    private String line1;

    private String line2;

    private String city;

    private String state;

    private String country;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @OneToOne(targetEntity = EmployeeDB.class)
    private EmployeeDB employee;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public EmployeeDB getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDB employee) {
        this.employee = employee;
    }
}

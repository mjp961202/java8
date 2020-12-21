package com.example.demo.service;

import com.example.demo.domain.Employee;

public class MyPredicateImpl2 implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee employee) {
        return employee.getSalary()>8000;
    }
}

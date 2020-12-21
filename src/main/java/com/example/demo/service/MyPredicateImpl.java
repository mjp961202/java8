package com.example.demo.service;

import com.example.demo.domain.Employee;

public class MyPredicateImpl implements MyPredicate<Employee> {
    @Override
    public boolean test(Employee employee) {
        return employee.getAge()>30;
    }
}

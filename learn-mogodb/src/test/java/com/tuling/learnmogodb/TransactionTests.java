package com.tuling.learnmogodb;

import com.tuling.learnmogodb.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionTests {
    @Autowired
    EmployeeService employeeService;

    @Test
    public void test(){
        employeeService.addEmployee();
    }
}

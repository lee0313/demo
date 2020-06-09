package com.bruce.demo;

import static org.junit.jupiter.api.Assertions.*;

import com.bruce.demo.resource.DepartmentResource;
import com.bruce.demo.resource.EmployeeResource;
import com.bruce.demo.service.DepartmentService;
import com.bruce.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoAppTests {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private EmployeeResource employeeResource;

	@Autowired
	private DepartmentResource departmentResource;

	@Test
	void contextLoads() {
		assertNotNull(employeeService);
		assertNotNull(departmentService);
		assertNotNull(employeeResource);
		assertNotNull(departmentResource);
	}

}

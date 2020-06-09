package com.bruce.demo;

import com.bruce.demo.model.Employee;
import com.bruce.demo.service.EmployeeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class DemoApp {

	public static void main(String[] args) {

		SpringApplication.run(DemoApp.class, args);
		/*
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoApp.class, args);

		EmployeeService service = ctx.getBean(EmployeeService.class);

		Employee emp = new Employee();

		emp.setId(1L);
		emp.setName("bruce");

		service.create(emp);

		Optional<Employee> optEmp = service.find(1L);

		System.out.println("name:" + optEmp.get().getName());
		*/

	}

}

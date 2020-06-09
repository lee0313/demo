package com.bruce.demo.resource;

import com.bruce.demo.condition.QueryCondition;
import com.bruce.demo.model.Employee;
import com.bruce.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeResource {

    private final EmployeeService service;

    @Autowired
    public EmployeeResource(final EmployeeService service) {
        this.service = service;
    }

    @PostMapping("/" + Path.EMPLOYEES)
    public Employee create(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @PutMapping("/" + Path.EMPLOYEES)
    public Employee update(@RequestBody Employee employee) {
        return service.update(employee);
    }

    @DeleteMapping("/" + Path.EMPLOYEES + "/{empId}")
    public void delete(@PathVariable Long empId) {
        service.delete(empId);
    }

    @GetMapping("/" + Path.EMPLOYEES)
    public List<Employee> findByQueryCondition(QueryCondition condition) {
        return service.findByQueryCondition(condition).getContent();
    }
}

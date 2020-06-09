package com.bruce.demo.resource;

import com.bruce.demo.model.Department;
import com.bruce.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentResource {

    private final DepartmentService service;

    @Autowired
    public DepartmentResource(final DepartmentService service) {
        this.service = service;
    }

    @PostMapping("/" + Path.DEPARTMENTS)
    public Department create(@RequestBody Department department) {
        return service.create(department);
    }

    @PutMapping("/" + Path.DEPARTMENTS)
    public Department update(@RequestBody Department department) {
        return service.update(department);
    }

    @DeleteMapping("/" + Path.DEPARTMENTS + "/{depId}")
    public void delete(@PathVariable String depId) {
        this.service.delete(depId);
    }

}

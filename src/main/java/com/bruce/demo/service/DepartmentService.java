package com.bruce.demo.service;

import com.bruce.demo.model.Department;
import com.bruce.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    @Autowired
    public DepartmentService(final DepartmentRepository repository) {
        this.repository = repository;
    }

    public Department create(final Department department) {

        final String depId = department.getId();
        if (this.repository.existsById(depId)) {
            throw new EntityExistsException();
        }
        return this.repository.save(department);
    }

    public Department update(final Department department) {

        final String depId = department.getId();
        if (!this.repository.existsById(depId)) {
            throw new EntityNotFoundException();
        }
        return this.repository.save(department);
    }

    public void delete(String depId) {
        if (!this.repository.existsById(depId)) {
            throw new EntityNotFoundException();
        }
        this.repository.deleteById(depId);
    }

}

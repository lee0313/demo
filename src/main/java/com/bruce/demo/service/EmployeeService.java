package com.bruce.demo.service;

import com.bruce.demo.condition.QueryCondition;
import com.bruce.demo.model.Employee;
import com.bruce.demo.repository.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeService(final EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee create(Employee employee) {

        final Long empId = employee.getId();
        if (this.repository.existsById(empId)) {
            throw new EntityExistsException();
        }
        return this.repository.save(employee);
    }

    public Employee update(Employee employee) {

        final Long empId = employee.getId();
        if (!this.repository.existsById(empId)) {
            throw new EntityNotFoundException();
        }
        return this.repository.save(employee);
    }

    public void delete(Long empId) {
        if (!this.repository.existsById(empId)) {
            throw new EntityNotFoundException();
        }
        this.repository.deleteById(empId);
    }


    public Page<Employee> findByQueryCondition(QueryCondition condition) {

        return this.repository.findAll(toSpecification(condition), condition.buildPageRequest());
    }


    private Specification<Employee> toSpecification(QueryCondition condition) {

        return (Specification<Employee>) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (!Objects.isNull(condition.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), condition.getId()));
            }

            if (!Objects.isNull(condition.getAge())) {
                predicates.add(criteriaBuilder.equal(root.get("age"), condition.getAge()));
            }

            if (StringUtils.isNotBlank(condition.getName())) {
                predicates.add(criteriaBuilder.equal(root.get("name"), condition.getName()));
            }

            if (StringUtils.isNotBlank(condition.getDepName())) {
                predicates.add(criteriaBuilder.like(root.get("depName"), '%' + condition.getDepName() + '%'));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

    }
}

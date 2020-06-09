package com.bruce.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import com.bruce.demo.condition.QueryCondition;
import com.bruce.demo.model.Department;
import com.bruce.demo.model.Employee;
import com.bruce.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@DataJpaTest
public class EmployeeServiceTest {

    private static final long EMPLOYEE_ID = 1;

    private static final String EMPLOYEE_NAME = "empName";

    private static final String DEPARTMENT_ID = "depId";

    private static final String DEPARTMENT_NAME = "depName";

    private EmployeeService target;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void init() {
        target = new EmployeeService(repository);
    }

    @AfterEach
    void after() {
        repository.deleteAll();
    }

    @Test
    public void testCreateEmployeeExist() {

        // Given
        Employee given = stubEmployee();
        repository.save(given);

        // When
        assertThrows(EntityExistsException.class, () ->
                target.create(given)
        );
    }

    @Test
    public void testCreateEmployeeSuccess() {

        // Given
        Employee given = stubEmployee();

        // When
        target.create(given);

        // Then
        Optional<Employee> persisted = repository.findById(given.getId());
        assertTrue(persisted.isPresent());
        assertEquals(given.getName(), persisted.get().getName());
    }

    @Test
    public void testUpdateEmployeeNotExist() {

        // Given
        Employee given = stubEmployee();

        // When
        assertThrows(EntityNotFoundException.class, () ->
                target.update(given)
        );
    }

    @Test
    public void testUpdateEmployeeSuccess() {

        // Given
        Employee given = stubEmployee(EMPLOYEE_ID, EMPLOYEE_NAME, DEPARTMENT_ID, DEPARTMENT_NAME);
        repository.save(given);

        // When
        final String givenNewName = "givenNewName";
        given.setName(givenNewName);
        target.update(given);

        // Then
        Optional<Employee> persisted = repository.findById(EMPLOYEE_ID);
        assertTrue(persisted.isPresent());
        assertEquals(givenNewName, persisted.get().getName());
        assertEquals(DEPARTMENT_NAME, persisted.get().getDepName());
    }

    @Test
    public void testDeleteEmployeeNotExist() {

        // Given
        Employee given = stubEmployee();
        final long givenEmpId = given.getId();

        // When
        assertThrows(EntityNotFoundException.class, () ->
                target.delete(givenEmpId)
        );
    }

    @Test
    public void testDeleteEmployeeSuccess() {

        // Given
        Employee given = repository.save(stubEmployee(EMPLOYEE_ID, EMPLOYEE_NAME, DEPARTMENT_ID, DEPARTMENT_NAME));

        assertTrue(Objects.nonNull(given));

        // When
        target.delete(EMPLOYEE_ID);

        // Then
        Optional<Employee> persisted = repository.findById(EMPLOYEE_ID);

        assertFalse(persisted.isPresent());
    }

    @Test
    public void testFindByQueryCondition() {

        // Given multiple employee has same name but different Id.
        final int givenSize = 3;
        IntStream.range(0, givenSize).forEach(i ->
            repository.save(stubEmployee(Long.valueOf(i), EMPLOYEE_NAME, DEPARTMENT_ID, DEPARTMENT_NAME))
        );

        // Given department for column join
        Department dep = new Department();
        dep.setId(DEPARTMENT_ID);
        dep.setName(DEPARTMENT_NAME);
        em.persist(dep);

        // When query by name
        QueryCondition queryByEmpName = new QueryCondition();
        queryByEmpName.setName(EMPLOYEE_NAME);

        Page<Employee> queryByEmpNameResult = target.findByQueryCondition(queryByEmpName);

        // Then result should be given size
        assertEquals(givenSize, queryByEmpNameResult.getContent().size());

        // When page size changed
        final int givenPageSize = 1;
        QueryCondition pageQuery = new QueryCondition();
        pageQuery.setName(EMPLOYEE_NAME);
        pageQuery.setPageSize(givenPageSize);

        Page<Employee> pageQueryResult = target.findByQueryCondition(pageQuery);

        // Then the fetched number should be as new page size
        assertEquals(givenPageSize, pageQueryResult.getContent().size());

        // When query by department name, which is joined from another entity.
        QueryCondition depNameQuery = new QueryCondition();
        depNameQuery.setDepName(DEPARTMENT_NAME);

        Page<Employee> depNameQueryResult = target.findByQueryCondition(depNameQuery);

        // Then result is not empty
        assertFalse(depNameQueryResult.getContent().isEmpty());

        depNameQueryResult.getContent().forEach(emp ->
            assertEquals(DEPARTMENT_NAME, emp.getDepName())
        );
    }

    private Employee stubEmployee() {
        return this.stubEmployee(EMPLOYEE_ID, EMPLOYEE_NAME, DEPARTMENT_ID, DEPARTMENT_NAME);
    }

    private Employee stubEmployee(long empId, String empName, String depId, String depName) {
        Employee emp = new Employee();
        emp.setId(empId);
        emp.setName(empName);
        emp.setDepId(depId);
        emp.setDepName(depName);
        return emp;
    }
}

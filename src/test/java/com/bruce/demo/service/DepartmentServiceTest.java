package com.bruce.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.bruce.demo.model.Department;
import com.bruce.demo.repository.DepartmentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    private static final String DEPARTMENT_ID = "depId";

    private static final String DEPARTMENT_NAME = "depName";

    @InjectMocks
    private DepartmentService target;

    @Mock
    private DepartmentRepository repository;

    @Test
    public void testCreateDepartmentAlreadyExist() {

        // Given
        when(repository.existsById(DEPARTMENT_ID)).thenReturn(Boolean.TRUE);

        // When
        assertThrows(EntityExistsException.class, () ->
            target.create(stubDepartment())
        );

    }

    @Test
    public void testCreateDepartmentSuccess() {

        // Given
        when(repository.existsById(DEPARTMENT_ID)).thenReturn(Boolean.FALSE);
        when(repository.save(any(Department.class))).thenReturn(stubDepartment());

        // When
        Department created = target.create(stubDepartment());

        // Then
        assertEquals(DEPARTMENT_ID, created.getId());
        assertEquals(DEPARTMENT_NAME, created.getName());
    }

    @Test
    public void testUpdateDepartmentNotExist() {

        // Given
        when(repository.existsById(DEPARTMENT_ID)).thenReturn(Boolean.FALSE);

        // When
        assertThrows(EntityNotFoundException.class, () ->
            target.update(stubDepartment())
        );
    }

    @Test
    public void testUpdateDepartmentSuccess() {

        // Given
        Department givenDep = stubDepartment();
        when(repository.existsById(anyString())).thenReturn(Boolean.TRUE);
        when(repository.save(eq(givenDep))).thenReturn(givenDep);

        // When
        Department updated = target.update(givenDep);

        // Then
        assertEquals(givenDep.getName(), updated.getName());
    }

    @Test
    public void testDeleteDepartmentNotExist() {

        // Given
        when(repository.existsById(DEPARTMENT_ID)).thenReturn(Boolean.FALSE);

        // When
        assertThrows(EntityNotFoundException.class, () ->
                target.delete(DEPARTMENT_ID)
        );
    }

    @Test
    public void testDeleteDepartmentSuccess() {

        // Given
        when(repository.existsById(DEPARTMENT_ID)).thenReturn(Boolean.TRUE);

        // When
        target.delete(DEPARTMENT_ID);

        // Then
        verify(repository).deleteById(DEPARTMENT_ID);
    }

    private Department stubDepartment() {
        return stubDepartment(DEPARTMENT_ID, DEPARTMENT_NAME);
    }

    private Department stubDepartment(String depId, String depName) {
        Department dep = new Department();
        dep.setId(depId);
        dep.setName(depName);
        return dep;
    }
}

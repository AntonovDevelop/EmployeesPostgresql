package com.company.repository;

import com.company.dao.Dao;
import com.company.dao.DepartmentDao;
import com.company.exceptions.NonExistentCustomerException;
import com.company.exceptions.NonExistentEntityException;
import com.company.model.Department;

import java.util.Collection;
import java.util.Optional;

public class DepartmentRepository {
    private static final Dao<Department, Integer> departmentDao = new DepartmentDao();

    public static Department getDepartment(int id) throws NonExistentEntityException {
        Optional<Department> department = departmentDao.get(id);
        return department.orElseThrow(NonExistentCustomerException::new);
    }

    public static Collection<Department> getAllDepartments() {
        return departmentDao.getAll();
    }

    public static void updateDepartment(Department department) {
        departmentDao.update(department);
    }

    public static Optional<Integer> addDepartment(Department department) {
        return departmentDao.save(department);
    }

    public static void deleteDepartment(Department department) {
        departmentDao.delete(department);
    }
}

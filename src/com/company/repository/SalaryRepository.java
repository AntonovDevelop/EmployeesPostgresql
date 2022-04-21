package com.company.repository;

import com.company.dao.Dao;
import com.company.dao.SalaryDao;
import com.company.exceptions.NonExistentCustomerException;
import com.company.exceptions.NonExistentEntityException;
import com.company.model.Salary;

import java.util.Collection;
import java.util.Optional;

public class SalaryRepository {
    private static final Dao<Salary, Integer> salaryDao = new SalaryDao();

    public static Salary getSalary(int id) throws NonExistentEntityException {
        Optional<Salary> salary = salaryDao.get(id);
        return salary.orElseThrow(NonExistentCustomerException::new);
    }

    public static Collection<Salary> getAllSalarys() {
        return salaryDao.getAll();
    }

    public static void updateSalary(Salary salary) {
        salaryDao.update(salary);
    }

    public static Optional<Integer> addSalary(Salary salary) {
        return salaryDao.save(salary);
    }

    public static void deleteSalary(Salary salary) {
        salaryDao.delete(salary);
    }
}

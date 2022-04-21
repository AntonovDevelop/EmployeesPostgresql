package com.company.repository;

import com.company.dao.CustomerDao;
import com.company.dao.Dao;
import com.company.exceptions.NonExistentCustomerException;
import com.company.exceptions.NonExistentEntityException;
import com.company.model.Customer;

import java.util.Collection;
import java.util.Optional;

public class CustomerRepository {
    private static final Dao<Customer, Integer> customerDao = new CustomerDao();

    public static Customer getCustomer(int id) throws NonExistentEntityException {
        Optional<Customer> customer = customerDao.get(id);
        return customer.orElseThrow(NonExistentCustomerException::new);
    }

    public static Collection<Customer> getAllCustomers() {
        return customerDao.getAll();
    }

    public static void updateCustomer(Customer customer) {
        customerDao.update(customer);
    }

    public static Optional<Integer> addCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    public static void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }
}

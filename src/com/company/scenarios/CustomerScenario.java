package com.company.scenarios;

import com.company.model.Customer;
import com.company.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerScenario {
    private static final CustomerRepository repo = new CustomerRepository();
    public static void play(){
        //Добавление покупателей
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("CustomerA", "CustomerA", "A@gmail.com"));
        customers.add(new Customer("CustomerB", "CustomerB", "B@gmail.com"));
        customers.add(new Customer("CustomerC", "CustomerC", "C@gmail.com"));
        for(Customer customer: customers){
            repo.addCustomer(customer).ifPresent(customer::setId);
        }
        //Редактируем добавленных прежде в базу покупателей
        customers.get(0).setFirstName("CustomerAA");
        customers.get(0).setLastName("CustomerAA");
        customers.get(0).setEmail("AA@gmail.com");
        repo.updateCustomer(customers.get(0));
        //Получаем всех покупателей
        System.out.println("Все покупатели:");
        repo.getAllCustomers().forEach(System.out::println);
        //Удаление покупателя
        //deleteCustomer(secondCustomer);
    }
}

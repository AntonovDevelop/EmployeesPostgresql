package com.company;

import com.mongodb.client.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

import com.company.dao.MongoDao;
import com.company.model.Employee;
import com.company.repository.EmployeeRepository;
import com.company.scenarios.EmployeeScenario;
import org.bson.Document;

public class Main {
    //для работы с redis
    private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;
    private static JedisPool pool = null;
    //
    public static void main(String[] args) {
        //переносит данные из postgres в mongodb и делает замер времени
        toMongoTransfer();
        //переносит данные из postgres в redis и делает замер времени
        //redisTest();
    }
    public static void toMongoTransfer(){
        //postgresql источник данных
        var data = new MongoDao().getAll();

        //работа с mongodb, получаю бд и коллекцию
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("sampleDB");
        MongoCollection col = db.getCollection("sampleCollection");
        //создаю документ и добавляю в коллекцию
        for(var item:data){
            Document document = new Document();
            document.append("familiya", item.getFamiliya());
            document.append("imya", item.getImya());
            document.append("otchestvo", item.getOtchestvo());
            document.append("doplata", item.getDoplata());
            document.append("department_name", item.getDepartment_name());
            document.append("position_name", item.getPosition_name());
            document.append("oklad", item.getOklad());
            document.append("stavka", item.getStavka());
            col.insertOne(document);
        }
        //считаю время
        int sum=0;
        for(int i=0;i<100;i++) {
            var before = System.currentTimeMillis();
            MongoCollection col1 = db.getCollection("sampleCollection");
            FindIterable<Document> documents = col1.find();
            var after = System.currentTimeMillis();
            System.out.println(after - before);
            sum+=after-before;
        }
        //итог усредненное время
        System.out.println("MONGO " + sum/100);
        //
        //можно прочитать данные
        /*for(Document doc: documents){
            System.out.println(doc);
        }*/
        client.close();
    }
    public static void redisTest(){
        //соединение с redis
        pool = new JedisPool(redisHost, redisPort);
        //добавление данных в виде hash
        addHash();
    }
    public static void addHash() {
        //postgresql источник данных
        var data = new MongoDao().getAll();
        //добавляю данные в redis
        Jedis jedis = pool.getResource();
        try {
            for(var item:data) {
                String key = item.getFamiliya() + item.getImya() + item.getOtchestvo();
                Map<String, String> map = new HashMap<>();
                map.put("doplata", String.valueOf(item.getDoplata()));
                map.put("department_name", item.getDepartment_name());
                map.put("position_name", item.getPosition_name());
                map.put("oklad", String.valueOf(item.getOklad()));
                map.put("stavka", String.valueOf(item.getStavka()));
                //само сохранение
                jedis.hmset(key, map);
            }

            //считаю время и получаю данные
            int timeSum = 0;
            for(int i=0;i<100;i++) {
                var before = System.currentTimeMillis();
                for (var item : data) {
                    String key = item.getFamiliya() + item.getImya() + item.getOtchestvo();
                    Map<String, String> retrieveMap = jedis.hgetAll(key);
                    //можно получить данные
                    /*for (String keyMap : retrieveMap.keySet()) {
                        System.out.println(keyMap + " " + retrieveMap.get(keyMap));
                    }*/
                }
                var after = System.currentTimeMillis();
                timeSum += after - before;
                System.out.println(after - before);
            }
            //итог усредненное время
            System.out.println("REDIS " + timeSum/100);
        } catch (JedisException e) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                pool.returnResource(jedis);
        }
    }
    //остальные методы из 5ой лабы
    public static void sc(){
        var repo=new EmployeeRepository();
        var sc=new Scanner(System.in);
        boolean exit=false;
        while (!exit) {
            System.out.println("1-Создать, 2-Обновить," +
                    " 3-Удалить, 4-Прочитать одного, 5-Прочитать всех 6-Выйти");
            int selection = sc.nextInt();
            int id;
            double doplata;
            String familiya, imya, otchestvo = "";
            switch (selection) {
                case 1:
                    System.out.println("Введите фио и доплату сотрудника");
                    familiya = sc.next();
                    imya = sc.next();
                    otchestvo = sc.next();
                    doplata = sc.nextDouble();
                    Employee em=new Employee(familiya, imya, otchestvo, doplata);
                    System.out.println(em);
                    repo.addEmployee(new Employee(familiya, imya, otchestvo, doplata));
                    break;
                case 2:
                    System.out.println("Введите фио сотрудника");
                    familiya = sc.next();
                    imya = sc.next();
                    otchestvo = sc.next();
                    if(!repo.getEmployeesByName(imya).isEmpty())
                        for(var employee:repo.getEmployeesByName(imya)){
                            if(employee.getFamiliya().equals(familiya) && employee.getOtchestvo().equals(otchestvo)) {
                                System.out.println("Введите число изменяемых параметров");
                                var n=sc.nextInt();
                                System.out.println("Введите номера изменяемых параметров");
                                var list = new ArrayList<Integer>();
                                for(int i=0;i<n;i++) {
                                    list.add(sc.nextInt());
                                }
                                System.out.println("Введите");
                                if(list.contains(1)) {
                                    System.out.print("Фамилию: ");
                                    familiya = sc.next();
                                    employee.setFamiliya(familiya);
                                }
                                if(list.contains(2)) {
                                    System.out.print("Имя: ");
                                    imya = sc.next();
                                    employee.setImya(imya);
                                }
                                if(list.contains(3)) {
                                    System.out.print("Отчество: ");
                                    otchestvo = sc.next();
                                    employee.setOtchestvo(otchestvo);
                                }
                                if(list.contains(4)) {
                                    System.out.print("Доплату: ");
                                    doplata = sc.nextDouble();
                                    employee.setDoplata(doplata);
                                }
                                repo.updateEmployee(employee);
                                break;
                            }
                    }
                    else {
                        System.out.println("Нет такого человека!");
                        break;
                    }
                    break;
                case 3:
                    System.out.println("Введите фио сотрудника");
                    familiya = sc.next();
                    imya = sc.next();
                    otchestvo = sc.next();
                    if(!repo.getEmployeesByName(imya).isEmpty())
                        for(var employee:repo.getEmployeesByName(imya)){
                            if(employee.getFamiliya().equals(familiya) && employee.getOtchestvo().equals(otchestvo)) {
                                repo.deleteEmployee(employee);
                                System.out.println("Удалили " + employee);
                                break;
                            }
                        }
                    break;
                case 4:
                    System.out.println("Введите фио сотрудника");
                    familiya = sc.next();
                    imya = sc.next();
                    otchestvo = sc.next();
                    if(!repo.getEmployeesByName(imya).isEmpty())
                        for(var employee:repo.getEmployeesByName(imya)){
                            if(employee.getFamiliya().equals(familiya) && employee.getOtchestvo().equals(otchestvo)) {
                                System.out.println(employee);
                            }
                        }
                    break;
                case 5:
                    System.out.println("Сотрудники");
                    for(var employee:repo.getAllEmployees()){
                        System.out.println(employee);
                    }
                    break;
                case 6:
                    exit=true;
                    break;
            }
        }
    }
    public static double sc1(EmployeeScenario employeeScenario){
        double sum=0;
        for(int i=0;i<1000;i++) {
            double startTime = System.currentTimeMillis();
            employeeScenario.playUpdates();
            double stopTime = System.currentTimeMillis();
            sum+=stopTime-startTime;
        }
        return sum/10;
    }
    public static double sc2(EmployeeScenario employeeScenario){
        double sum=0;
        for(int i=0;i<1000;i++) {
            double startTime = System.currentTimeMillis();
            employeeScenario.playGetEmployeesByName();
            double stopTime = System.currentTimeMillis();
            sum+=stopTime-startTime;
        }
        return sum/10;
    }
    public static double sc3(EmployeeScenario employeeScenario){
        double sum=0;
        for(int i=0;i<1000;i++) {
            double startTime = System.currentTimeMillis();
            employeeScenario.playGetEmployeesGroupedByName();
            double stopTime = System.currentTimeMillis();
            sum+=stopTime-startTime;
        }
        return sum/10;
    }
    //        employeeScenario.createIndexes();
    //        double res = sc2(employeeScenario);
    //        employeeScenario.dropIndexes();
    //        System.out.println("Среднее :" + res);
}

package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.entities.Pay;
import com.ifox.hgx.hibernate.entities.Worker;
import com.ifox.hgx.hibernate.n21.Customer;
import com.ifox.hgx.hibernate.n21.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test3 {
    private Configuration cfg;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        cfg = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void after() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void TestComponent(){
        Worker worker = new Worker() ;
        Pay pay = new Pay() ;
        pay.setMonthlyPay(1000);
        pay.setYearPay(80000);
        pay.setVocationWithPay(6);

        worker.setName("asdas");
        worker.setPay(pay);

        session.save(worker) ;

    }

    @Test
    public  void testMany2One(){
        Customer customer = new Customer() ;
        customer.setCustomerName("BB");

        Order order1 = new Order() ;
        order1.setOrderName("ORDER_3");

        Order order2 = new Order() ;
        order2.setOrderName("ORDER_4");
//设置关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

//     先插入Customer，再插入Order，3条insert。
//      先插入1的一端，再插入n的一端，只有insert。
//        session.save(customer) ;
//        session.save(order1) ;
//        session.save(order2) ;

//     先插入order，再插入Customer，3条insert，2条update。
//      先插入n的一端，再插入1 的一端，会多出UPDATE语句。
//        因为在插入多的一端时，无法确定１的一端的外键值。
       session.save(order1) ;
       session.save(order2) ;
        session.save(customer) ;
    }
    @Test
    public void testMany2OneGet(){
//  如果查询多的一端的一个对象，默认只查询到多的一端的对象，而没有查询关联的1的那一端的对象
        Order order = session.get(Order.class,1) ;
        System.out.println(order.getOrderName());
//        在需要使用到关联的对象时，才发送对应的sql语句。
//        懒加载 可能发生懒加载异常。 如果此时session已经被关闭，默认情况下发生懒加载异常。
        Customer customer = order.getCustomer() ;
        System.out.println(customer.getCustomerName());

//        获得oder对象时，默认情况下，其关联的对象时一个代理对象.
    }

    @Test
    public  void testUpdate(){
        Order order = session.get(Order.class,1) ;
        order.getCustomer().setCustomerName("AAA");
    }
    @Test
    public void testDelete(){
        //在不设定级联关系的情况下，且1这一端对象有n的应用，不能删除1端。
        Customer customer = session.get(Customer.class,1) ;
        session.delete(customer);
    }
}

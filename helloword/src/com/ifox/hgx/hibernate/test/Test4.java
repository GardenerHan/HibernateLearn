package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.n21.both.Customer;
import com.ifox.hgx.hibernate.n21.both.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test4 {
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
    public void TestOne2ManyBothSave() {
        Customer customer = new Customer();
        Order order = new Order();
        Order order1 = new Order();
        customer.setCustomerName("KKK");
        order.setOrderName("Order_6");
        order1.setOrderName("Order_7");

        order.setCustomer(customer);
        order1.setCustomer(customer);

        customer.getOrders().add(order);
        customer.getOrders().add(order1);
//     先插入Customer，再插入Order，3条insert,2update。
//      因为1的一端和n的一端都维护关联关系，所以会多出两条Update。
//      怎么解决?1的一端set节点下inverse = true 来让他放弃维护关系。
//        建议使用先插入1的一端，减少update。

//    级联更新
        session.save(customer) ;
//        session.save(order) ;
//        session.save(order1) ;

//      先插入Order，再i插入Customer，3条insert，4条update.
//        session.save(order) ;
//        session.save(order1) ;
//        session.save(customer) ;
    }

    @Test
    public  void  testOne2Many_both_Get(){
        //对n端延迟加载
        Customer customer = session.get(Customer.class,2) ;
        System.out.println(customer.getCustomerName());
        //返回多的一端的集合是hibernate的内置集合类型。
        //该类型具有延时加载和存放代理对象的功能。
        System.out.println(customer.getOrders().getClass());
        //可能抛出  懒加载异常。

        //在需要使用集合元素的时候初始化
    }
    @Test
    public  void testUpdate2(){
        Customer customer =session.get(Customer.class,1) ;
        customer.getOrders().iterator().next().setOrderName("GGG");
    }

    @Test
    public void  testCascade() {
        Customer customer = session.get(Customer.class, 1);
        customer.getOrders().clear();
    }
}
